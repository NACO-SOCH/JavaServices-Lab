package gov.naco.soch.lab.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import gov.naco.soch.dto.LoginResponseDto;
import gov.naco.soch.entity.Address;
import gov.naco.soch.entity.Beneficiary;
import gov.naco.soch.entity.BeneficiaryFamilyDetail;
import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.IctcSampleCollection;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterInfantBreastFeed;
import gov.naco.soch.entity.MasterRemark;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.Test;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.mapper.AdvanceSearchMapperUtil;
import gov.naco.soch.lab.mapper.ReceiveSamplesServiceMapperUtil;
import gov.naco.soch.repository.BeneficiaryFamilyDetailRepository;
import gov.naco.soch.repository.IctcSampleCollectionRepository;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterInfantBreastFeedRepository;
import gov.naco.soch.repository.MasterRemarkRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;
import gov.naco.soch.util.UserUtils;

@Service
@Transactional
public class ReceiveSamplesService {

	private static String ACCEPT = "ACCEPT";

	private static String REJECT = "REJECT";

	private static String NOT_RECEIVED = "NOT RECEIVED";

	private static String RECIEVED = "RECIEVED";

	private static String REJECTED = "REJECTED";

	private static String PARTIALLY_RECEIVED = "PARTIALLY RECEIVED";

	@Autowired
	private LabTestSampleBatchRepository labTestSampleBatchRepository;

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Autowired
	private MasterSampleStatusRepository masterSampleStatusRepository;

	@Autowired
	private MasterBatchStatusRepository masterBatchStatusRepository;

	@Autowired
	private MasterResultStatusRepository masterResultStatusRepository;

	@Autowired
	private MasterRemarkRepository masterRemarkRepository;

	@Autowired
	private MasterInfantBreastFeedRepository masterInfantBreastFeedRepository;

	@Autowired
	private IctcSampleCollectionRepository ictcSampleCollectionRepository;

	@Autowired
	private BeneficiaryFamilyDetailRepository beneficiaryFamilyDetailRepository;

	private static final Logger logger = LoggerFactory.getLogger(ReceiveSamplesService.class);

	public List<LabTestSampleBatchDto> fetchReceiveSamplesList(Long labId) {
		logger.debug("In fetchReceiveSamplesList() of RecieveSamplesService");

		List<LabTestSampleBatchDto> labTestSampleBatchDtoList = new ArrayList<>();

		List<LabTestSampleBatch> labTestSampleBatchList = labTestSampleBatchRepository.findByLabIdAndIsDelete(labId,
				Boolean.FALSE);
		if (!CollectionUtils.isEmpty(labTestSampleBatchList)) {

			labTestSampleBatchList.forEach(l -> {
				LabTestSampleBatchDto labTestSampleBatchDto = ReceiveSamplesServiceMapperUtil
						.mapToLabTestSampleBatchDto(l);
				labTestSampleBatchDtoList.add(labTestSampleBatchDto);
			});
			fetchIctcInfantDetails(labTestSampleBatchDtoList);
			findPreviousDBSDetails(labTestSampleBatchDtoList);
		}
		return labTestSampleBatchDtoList.stream()
				.sorted(Comparator.comparing(LabTestSampleBatchDto::getBatchId).reversed())
				.collect(Collectors.toList());
	}

	public LabTestSampleBatchDto saveReceivedSamples(Long batchId, LabTestSampleBatchDto labTestSampleBatchDto) {

		Optional<LabTestSampleBatch> labTestSampleBatchOpt = labTestSampleBatchRepository.findById(batchId);
		if (labTestSampleBatchOpt.isPresent()) {
			LabTestSampleBatch labTestSampleBatch = labTestSampleBatchOpt.get();
			labTestSampleBatch.setAcceptedSamples(labTestSampleBatchDto.getAcceptedSamples());
			labTestSampleBatch.setRejectedSamples(labTestSampleBatchDto.getRejectedSamples());
			labTestSampleBatch.setReceivedDate(LocalDateTime.now());
			Facility lab = labTestSampleBatch.getLab();
			Optional<UserMaster> labTechUserOpt = userMasterRepository
					.findById(labTestSampleBatchDto.getLabTechnicianId());
			if (labTechUserOpt.isPresent()) {
				labTestSampleBatch.setVlLabTechUser(labTechUserOpt.get());
			} else {
				throw new ServiceException("Invalid User", null, HttpStatus.BAD_REQUEST);
			}

			if (!CollectionUtils.isEmpty(labTestSampleBatch.getLabTestSamples())
					&& !CollectionUtils.isEmpty(labTestSampleBatchDto.getLabTestSampleDtoList())) {

				MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("PENDING",
						Boolean.FALSE);

				labTestSampleBatch.getLabTestSamples().forEach(s -> {

					Optional<LabTestSampleDto> sampleOpt = labTestSampleBatchDto.getLabTestSampleDtoList().stream()
							.filter(ts -> ts.getSampleId() == s.getId()).findFirst();
					if (sampleOpt.isPresent()) {
						LabTestSampleDto sample = sampleOpt.get();
						s.setLabTecnician(labTechUserOpt.get());
						if (sample.getSampleStatusId() != null && sample.getSampleStatusId() != 0L) {
							Optional<MasterSampleStatus> sampleStatusOpt = masterSampleStatusRepository
									.findById(sample.getSampleStatusId());
							if (sampleStatusOpt.isPresent()) {
								s.setSampleReceivedDate(LocalDateTime.now());
								s.setMasterSampleStatus(sampleStatusOpt.get());
								if (sampleStatusOpt.get().getStatus().equalsIgnoreCase(ACCEPT)) {
									s.setArtcSampleStatus(RECIEVED);
								}
								if (sampleStatusOpt.get().getStatus().equalsIgnoreCase(REJECT)) {
									s.setArtcSampleStatus(REJECTED);
								}
								if (sampleStatusOpt.get().getStatus().equalsIgnoreCase(NOT_RECEIVED)) {
									s.setArtcSampleStatus(NOT_RECEIVED);
								}
							} else {
								throw new ServiceException("Invalid Status", null, HttpStatus.BAD_REQUEST);
							}
						}

						if (sample.getRemarksId() != null && sample.getRemarksId() != 0L) {
							Optional<MasterRemark> remarkOpt = masterRemarkRepository.findById(sample.getRemarksId());
							if (remarkOpt.isPresent()) {
								s.setMasterRemark(remarkOpt.get());
							} else {
								throw new ServiceException("Invalid Status", null, HttpStatus.BAD_REQUEST);
							}
						}

						s.setMasterResultStatus(masterResultStatus);

						if (lab.getFacilityType().getId() == 20L) {
							Test test = new Test();
							test.setId(4L);
							s.setTest(test);

							LoginResponseDto userLoginDetials = UserUtils.getLoggedInUserDetails();
							Facility dispatedTo = new Facility();
							dispatedTo.setId(userLoginDetials.getFacilityId());
							s.setDispatchedToLab(dispatedTo);
						}
					}
				});
				String batchStatus = findBatchStatus(labTestSampleBatch.getLabTestSamples());
				MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete(batchStatus,
						Boolean.FALSE);
				if (masterBatchStatus != null) {
					labTestSampleBatch.setMasterBatchStatus(masterBatchStatus);
				}
			}

			LabTestSampleBatch labTestSampleBatchSaved = labTestSampleBatchRepository.save(labTestSampleBatch);
			updateIctc(labTestSampleBatchSaved);
			return ReceiveSamplesServiceMapperUtil.mapToLabTestSampleBatchDto(labTestSampleBatchSaved);
		} else {
			// throw error
		}
		return labTestSampleBatchDto;
	}

	private String findBatchStatus(Set<LabTestSample> labTestSamples) {
		int acceptCount = 0;
		int rejectCount = 0;
		int notRecievedCount = 0;
		int samplesCount = labTestSamples.size();
		for (LabTestSample s : labTestSamples) {
			if (s.getMasterSampleStatus().getStatus().equalsIgnoreCase(ACCEPT)) {
				acceptCount++;
			}
			if (s.getMasterSampleStatus().getStatus().equalsIgnoreCase(REJECT)) {
				rejectCount++;
			}
			if (s.getMasterSampleStatus().getStatus().equalsIgnoreCase(NOT_RECEIVED)) {
				notRecievedCount++;
			}
		}
		if (acceptCount == samplesCount) {
			return RECIEVED;
		}
		if (rejectCount == samplesCount || notRecievedCount == samplesCount) {
			return REJECTED;
		}
		return PARTIALLY_RECEIVED;
	}

	private void updateIctc(LabTestSampleBatch labTestSampleBatch) {

		Facility facility = labTestSampleBatch.getFacility();
		if (facility != null && facility.getFacilityType() != null
				&& (facility.getFacilityType().getId() == 11L || facility.getFacilityType().getId() == 13L)) {

			List<String> barcodes = labTestSampleBatch.getLabTestSamples().stream().map(s -> s.getBarcodeNumber())
					.collect(Collectors.toList());
			if (!CollectionUtils.isEmpty(barcodes)) {

				List<IctcSampleCollection> samples = ictcSampleCollectionRepository.findBySampleBatchBarcodes(barcodes);
				if (!CollectionUtils.isEmpty(samples)) {

					samples.stream().forEach(s -> {
						Optional<LabTestSample> labSampleOpt = labTestSampleBatch.getLabTestSamples().stream()
								.filter(ls -> ls.getBarcodeNumber().equalsIgnoreCase(s.getBarcode())).findFirst();

						if (labSampleOpt.isPresent()) {

							Long sampleStatusId = labSampleOpt.get().getMasterSampleStatus().getId();
							if (sampleStatusId == 1L) {
								s.setSampleCollectionStatus(3L);
							}
							if (sampleStatusId == 2L) {
								s.setSampleCollectionStatus(4L);
							}
							if (sampleStatusId == 3L) {
								s.setSampleCollectionStatus(6L);
							}
							s.getBatch().setBatchStatus(labTestSampleBatch.getMasterBatchStatus().getId());
						}
					});
					ictcSampleCollectionRepository.saveAll(samples);
				}
			}
		}
	}

	private void fetchIctcInfantDetails(List<LabTestSampleBatchDto> labTestSampleBatchDtoList) {

		List<String> barcodes = labTestSampleBatchDtoList.stream().flatMap(b -> b.getLabTestSampleDtoList().stream())
				.map(s -> s.getBarcodeNumber()).collect(Collectors.toList());

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

			labTestSampleBatchDtoList.stream().flatMap(b -> b.getLabTestSampleDtoList().stream()).forEach(s -> {

				IctcSampleCollection ictcBenificiaryDetails = ictcBenificiaryDetailsMap.get(s.getBarcodeNumber());
				if (ictcBenificiaryDetails != null) {
					s.setInfantDnaCode(ictcBenificiaryDetails.getIctcBeneficiary().getInfantCode());
					s.setInfantPID(ictcBenificiaryDetails.getIctcBeneficiary().getPid());

					Optional<BeneficiaryFamilyDetail> motherDetilsOpt = beneficiaryFamilyDetailRepository
							.findByBeneficiaryIdAndRelationshipId(
									ictcBenificiaryDetails.getIctcBeneficiary().getBeneficiary().getId(), 4L);
					if (motherDetilsOpt.isPresent()) {
						Beneficiary motherDetils = motherDetilsOpt.get().getPartnerBeneficiary();

						s.setMotherId(motherDetils.getId());
						s.setMotherName(motherDetils.getFirstName() + " "
								+ (motherDetils.getLastName() != null ? motherDetils.getLastName() : ""));
						s.setMotherArtNumber(motherDetils.getArtNumber());
						s.setMotherPreArtNumber(motherDetils.getPreArtNumber());
						s.setMotherContact(motherDetils.getMobileNumber());
						if (motherDetils.getIctcBeneficiary() != null) {
							s.setMotherUid(motherDetils.getIctcBeneficiary().getPid());
						}

						Address motherAddress = motherDetils.getAddress();
						String motherAddressString = (motherAddress.getAddressLineOne() != null
								? motherAddress.getAddressLineOne()
								: "")
								+ (StringUtils.isEmpty(motherAddress.getAddressLineTwo()) ? "": ", " + motherAddress.getAddressLineTwo());
						s.setMotherAddress(motherAddressString);
					}


					if (ictcBenificiaryDetails.getVisit() != null) {
						s.setFeedingType(
								infantBreastStatusMap.get(ictcBenificiaryDetails.getVisit().getInfantBreastFed()));
					}
				}
			});
		}
	}

	void findPreviousDBSDetails(List<LabTestSampleBatchDto> labTestSampleBatchDtoList) {

		LoginResponseDto userLoginDetials = UserUtils.getLoggedInUserDetails();
		if (userLoginDetials.getFacilityTypeId() == 20L) {
			List<LabTestSampleDto> samples = labTestSampleBatchDtoList.stream()
					.flatMap(b -> b.getLabTestSampleDtoList().stream()).collect(Collectors.toList());

			for (LabTestSampleDto s : samples) {

				List<LabTestSample> previousSamples = labTestSampleRepository.findPreviousDBSDetails(
						s.getBeneficiaryId(), userLoginDetials.getFacilityId(), s.getSampleId());

				Optional<LabTestSample> previousSample = previousSamples.stream()
						.sorted(Comparator.comparing(LabTestSample::getId)).findFirst();
				if (previousSample.isPresent()) {

					s.setIsPreviousTestDone(Boolean.TRUE);
					if (previousSample.get().getMasterResultStatus() != null
							&& previousSample.get().getMasterResultStatus().getId() == 3L) {
						s.setPreviousTestDate(previousSample.get().getResultApprovedDate().toLocalDate());
						s.setPreviousTestResult(previousSample.get().getResultType().getResultType());
					}

				} else {
					s.setIsPreviousTestDone(Boolean.FALSE);
				}
			}
		}
	}

	public List<LabTestSampleBatchDto> getReceiveSamplesListByAdvanceSearch(Long labId,
			Map<String, String> searchValue) {
		List<LabTestSampleBatchDto> labTestSampleBatchDtoList = new ArrayList<>();
		List<String> searchQuery = AdvanceSearchMapperUtil.queryCreaterForAdvanceSearchReceiveSampleList(labId,
				searchValue);
		if (!searchQuery.isEmpty()) {
			List<LabTestSampleBatch> labTestSampleBatchList = labTestSampleBatchRepository
					.getReceiveSamplesListByAdvanceSearch(searchQuery.get(0));
			if (!CollectionUtils.isEmpty(labTestSampleBatchList)) {

				labTestSampleBatchList.forEach(l -> {
					LabTestSampleBatchDto labTestSampleBatchDto = ReceiveSamplesServiceMapperUtil
							.mapToLabTestSampleBatchDto(l);
					labTestSampleBatchDtoList.add(labTestSampleBatchDto);
				});
				fetchIctcInfantDetails(labTestSampleBatchDtoList);
				findPreviousDBSDetails(labTestSampleBatchDtoList);
			}
		}
		return labTestSampleBatchDtoList.stream()
				.sorted(Comparator.comparing(LabTestSampleBatchDto::getBatchId).reversed())
				.collect(Collectors.toList());
	}
}
