package gov.naco.soch.lab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.IctcSampleCollection;
import gov.naco.soch.entity.IctcTestResult;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterInfantBreastFeed;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.mapper.TestResultMapper;
import gov.naco.soch.repository.IctcSampleCollectionRepository;
import gov.naco.soch.repository.IctcTestResultRepository;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterInfantBreastFeedRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;

@Service
@Transactional
public class TestResultService {

	private static final Logger logger = LoggerFactory.getLogger(TestResultService.class);

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private MasterSampleStatusRepository masterSampleStatusRepository;

	@Autowired
	private MasterResultStatusRepository masterResultStatusRepository;

	@Autowired
	private MasterBatchStatusRepository masterBatchStatusRepository;

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Autowired
	private LabTestSampleBatchRepository labTestSampleBatchRepository;

	@Autowired
	private IctcSampleCollectionRepository ictcSampleCollectionRepository;

	@Autowired
	private MasterInfantBreastFeedRepository masterInfantBreastFeedRepository;

	@Autowired
	private IctcTestResultRepository ictcTestResultRepository;

	public List<TestResultDto> fetchTestResultsList(Long labId) {

		logger.debug("In fetchTestResultsList() of TestResultService");

		MasterSampleStatus masterSampleStatusAccepted = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
				Boolean.FALSE);

		MasterSampleStatus masterSampleStatusResultPosted = masterSampleStatusRepository
				.findByStatusAndIsDelete("RESULT POSTED", Boolean.FALSE);

		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("PENDING",
				Boolean.FALSE);

		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
				Boolean.FALSE);

		Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
				.getId() != masterBatchStatus.getId();

		Predicate<LabTestSample> isSampleInLab = s -> s.getLabTestSampleBatch().getLab().getId() == labId;

		Predicate<LabTestSample> sampleStatus = s -> s.getMasterSampleStatus().getId() == masterSampleStatusAccepted
				.getId() || s.getMasterSampleStatus().getId() == masterSampleStatusResultPosted.getId();

		Predicate<LabTestSample> checkResultStatus = s -> s.getMasterResultStatus().getId() != masterResultStatus
				.getId();

		List<TestResultDto> testResultDto = new ArrayList<>();
		List<LabTestSample> labTestSampleList = labTestSampleRepository.findByIsDelete(Boolean.FALSE);
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
			labTestSampleList = labTestSampleList.stream()
					.filter(isSampleInLab.and(sampleStatus).and(checkResultStatus)).collect(Collectors.toList());
			testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
					.collect(Collectors.toList());
			fetchIctcInfantDetails(testResultDto);
		}
		return testResultDto.stream().sorted(Comparator.comparing(TestResultDto::getBatchId).reversed())
				.collect(Collectors.toList());
	}

	public List<TestResultDto> fetchTestResultsUnderApproval(Long labId) {

		logger.debug("In fetchTestResultsUnderApproval() of TestResultService");

		MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
				Boolean.FALSE);

		MasterResultStatus masterResultStatus = masterResultStatusRepository
				.findByStatusAndIsDelete("AWAITING APPROVAL", Boolean.FALSE);

		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
				Boolean.FALSE);

		Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
				.getId() != masterBatchStatus.getId();

		Predicate<LabTestSample> isSampleInLab = s -> s.getLabTestSampleBatch().getLab().getId() == labId;

		Predicate<LabTestSample> statusAccepted = s -> s.getMasterSampleStatus().getId() == masterSampleStatus.getId();

		Predicate<LabTestSample> checkResultStatus = s -> s.getMasterResultStatus().getId() == masterResultStatus
				.getId();

		List<TestResultDto> testResultDto = new ArrayList<>();
		List<LabTestSample> labTestSampleList = labTestSampleRepository.findByIsDelete(Boolean.FALSE);
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
			labTestSampleList = labTestSampleList.stream()
					.filter(isSampleInLab.and(statusAccepted).and(checkResultStatus)).collect(Collectors.toList());
			testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
					.collect(Collectors.toList());
			fetchIctcInfantDetails(testResultDto);
		}
		return testResultDto.stream().sorted(Comparator.comparing(TestResultDto::getBatchId).reversed())
				.collect(Collectors.toList());
	}

	public List<TestResultDto> approveTestResults(Long labInchargeId, List<TestResultDto> testResultList) {

		List<Long> idList = testResultList.stream().map(s -> s.getSampleId()).collect(Collectors.toList());

		List<LabTestSample> labTestSampleList = labTestSampleRepository.findAllById(idList);

		MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("RESULT POSTED",
				Boolean.FALSE);

		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("APPROVED",
				Boolean.FALSE);

		UserMaster labIncharge = null;
		Optional<UserMaster> labInchargeOpt = userMasterRepository.findById(labInchargeId);
		if (labInchargeOpt.isPresent()) {
			labIncharge = labInchargeOpt.get();
		} else {
			throw new ServiceException("Invalid User", null, HttpStatus.BAD_REQUEST);
		}

		List<Long> batchIds = labTestSampleList.stream().map(s -> s.getLabTestSampleBatch().getId())
				.collect(Collectors.toList());

		List<TestResultDto> testResultDto = new ArrayList<>();
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			for (LabTestSample s : labTestSampleList) {
				s.setMasterSampleStatus(masterSampleStatus);
				s.setMasterResultStatus(masterResultStatus);
				s.setArtcSampleStatus("RESULT POSTED");
				s.setLabInCharge(labIncharge);
				s.setResultApprovedDate(LocalDateTime.now());
				s.setResultDispatchDate(LocalDateTime.now());
				s.setAuthorizer(labIncharge);
			}
			labTestSampleList = labTestSampleRepository.saveAll(labTestSampleList);

			testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
					.collect(Collectors.toList());
			// Change the status of batch
			changeBatchStatus(batchIds);
			updateIctc(labTestSampleList);
		}

		return testResultDto;
		// Handle the change of batch status
	}

	public List<TestResultDto> rejectTestResults(Long labInchargeId, List<TestResultDto> testResultList) {

		List<Long> idList = testResultList.stream().map(s -> s.getSampleId()).collect(Collectors.toList());

		List<LabTestSample> labTestSampleList = labTestSampleRepository.findAllById(idList);

		MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("RESULT POSTED",
				Boolean.FALSE);

		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("REJECTED",
				Boolean.FALSE);

		UserMaster labIncharge = null;
		Optional<UserMaster> labInchargeOpt = userMasterRepository.findById(labInchargeId);
		if (labInchargeOpt.isPresent()) {
			labIncharge = labInchargeOpt.get();
		} else {
			throw new ServiceException("Invalid User", null, HttpStatus.BAD_REQUEST);
		}

		List<TestResultDto> testResultDto = new ArrayList<>();
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			for (LabTestSample s : labTestSampleList) {
				s.setMasterSampleStatus(masterSampleStatus);
				s.setMasterResultStatus(masterResultStatus);
				s.setLabInCharge(labIncharge);
				s.setAuthorizer(labIncharge);
				s.setArtcSampleStatus("RESULT POSTED");
				s.setResultApprovedDate(LocalDateTime.now());
				s.setResultDispatchDate(LocalDateTime.now());
			}
			labTestSampleList = labTestSampleRepository.saveAll(labTestSampleList);

			testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
					.collect(Collectors.toList());

			updateIctc(labTestSampleList);
		}
		return testResultDto;
	}

	private void changeBatchStatus(List<Long> batchIds) {

		batchIds = batchIds.stream().distinct().collect(Collectors.toList());

		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("RESULT POSTED",
				Boolean.FALSE);

		List<LabTestSampleBatch> labTestSampleBatchList = labTestSampleBatchRepository.findAllById(batchIds);
		if (!CollectionUtils.isEmpty(labTestSampleBatchList)) {

			labTestSampleBatchList.stream().forEach(b -> {

				Boolean accepted = Boolean.FALSE;
				int acceptCount = 0;
				if (!CollectionUtils.isEmpty(b.getLabTestSamples())) {
					for (LabTestSample s : b.getLabTestSamples()) {
						if (s.getMasterResultStatus().getStatus().equalsIgnoreCase("APPROVED")) {
							acceptCount++;
						}
					}
					if (acceptCount == b.getLabTestSamples().size()) {
						accepted = Boolean.TRUE;
					}
				}
				if (accepted) {
					b.setMasterBatchStatus(masterBatchStatus);
				}
			});

			labTestSampleBatchRepository.saveAll(labTestSampleBatchList);
		}
	}

	private void updateIctc(List<LabTestSample> labTestSampleList) {

		List<String> barcodes = labTestSampleList.stream().map(s -> s.getBarcodeNumber()).collect(Collectors.toList());

		List<IctcSampleCollection> samples = ictcSampleCollectionRepository.findBySampleBatchBarcodes(barcodes);
		if (!CollectionUtils.isEmpty(samples)) {

			List<IctcSampleCollection> samplesToSave = new ArrayList<>();

			List<IctcTestResult> testResultToSave = new ArrayList<>();

			List<Long> sampleIds = samples.stream().map(IctcSampleCollection::getId).collect(Collectors.toList());

			List<IctcTestResult> testResult = ictcTestResultRepository.findBySampleIds(sampleIds);

			Map<Long, IctcTestResult> testResultMap = new HashMap<>();
			for (IctcTestResult t : testResult) {
				testResultMap.put(t.getSample().getId(), t);
			}

			Map<String, IctcSampleCollection> samplesMap = new HashMap<>();
			for (IctcSampleCollection s : samples) {
				samplesMap.put(s.getBarcode(), s);
			}

			labTestSampleList.stream().forEach(labTestSample -> {
				Facility facility = labTestSample.getLabTestSampleBatch().getFacility();
				if (facility != null && facility.getFacilityType() != null
						&& (facility.getFacilityType().getId() == 11L || facility.getFacilityType().getId() == 13L)) {

					IctcSampleCollection samplesOpt = samplesMap.get(labTestSample.getBarcodeNumber());

					if (samplesOpt != null) {

						IctcTestResult testResultOpt = testResultMap.get(samplesOpt.getId());

						if (testResultOpt != null) {
							samplesOpt.setSampleCollectionStatus(5L);
							testResultOpt.setResultStatus(labTestSample.getMasterResultStatus().getId());
							testResultOpt.setReportDeliveryDate(LocalDate.now());
							samplesToSave.add(samplesOpt);
							testResultToSave.add(testResultOpt);
						}
					}

				}
			});
			ictcSampleCollectionRepository.saveAll(samplesToSave);
			ictcTestResultRepository.saveAll(testResultToSave);
		}
	}

	private void fetchIctcInfantDetails(List<TestResultDto> testResultDto) {

		List<String> barcodes = testResultDto.stream().map(s -> s.getBarcodeNumber()).collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(barcodes)) {

			List<IctcSampleCollection> ictcSamples = ictcSampleCollectionRepository.findBySampleBatchBarcodes(barcodes);
			if (!CollectionUtils.isEmpty(ictcSamples)) {

				List<MasterInfantBreastFeed> infantBreastStatus = masterInfantBreastFeedRepository
						.findByIsDelete(Boolean.FALSE);

				Map<Long, String> infantBreastStatusMap = infantBreastStatus.stream()
						.collect(Collectors.toMap(MasterInfantBreastFeed::getId, MasterInfantBreastFeed::getName));

				Map<String, IctcSampleCollection> ictcBenificiaryDetailsMap = new HashMap<>();
				for (IctcSampleCollection s : ictcSamples) {
					ictcBenificiaryDetailsMap.put(s.getBarcode(), s);
				}

				testResultDto.stream().forEach(s -> {

					IctcSampleCollection ictcBenificiaryDetails = ictcBenificiaryDetailsMap.get(s.getBarcodeNumber());
					if (ictcBenificiaryDetails != null) {
						s.setInfantDnaCode(ictcBenificiaryDetails.getIctcBeneficiary().getInfantCode());
						s.setInfantPID(ictcBenificiaryDetails.getIctcBeneficiary().getPid());
//					if (ictcBenificiaryDetails.getIctcBeneficiary().getArtBeneficiaryDetails() != null) {
//						s.setMotherArtNumber(
//								ictcBenificiaryDetails.getIctcBeneficiary().getArtBeneficiaryDetails().getArtNumber());
//						s.setMotherPreArtNumber(ictcBenificiaryDetails.getIctcBeneficiary().getArtBeneficiaryDetails()
//								.getPreArtNumber());
//					}
						if (ictcBenificiaryDetails.getVisit() != null) {
							s.setFeedingType(
									infantBreastStatusMap.get(ictcBenificiaryDetails.getVisit().getInfantBreastFed()));
						}
					}
				});
			}
		}
	}
}
