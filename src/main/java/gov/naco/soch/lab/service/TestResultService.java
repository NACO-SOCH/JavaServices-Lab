package gov.naco.soch.lab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import gov.naco.soch.dto.LoginResponseDto;
import gov.naco.soch.entity.Address;
import gov.naco.soch.entity.Beneficiary;
import gov.naco.soch.entity.BeneficiaryFamilyDetail;
import gov.naco.soch.entity.BeneficiaryIctcStatusTracking;
import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.IctcSampleBatch;
import gov.naco.soch.entity.IctcSampleCollection;
import gov.naco.soch.entity.IctcTestResult;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterHivStatus;
import gov.naco.soch.entity.MasterHivType;
import gov.naco.soch.entity.MasterInfantBreastFeed;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.enums.FacilityTypeEnum;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.dto.TestSamplesResponseDto;
import gov.naco.soch.lab.mapper.AdvanceSearchMapperUtil;
import gov.naco.soch.lab.mapper.TestResultMapper;
import gov.naco.soch.projection.IctcTestResultProjection;
import gov.naco.soch.repository.BeneficiaryFamilyDetailRepository;
import gov.naco.soch.repository.BeneficiaryIctcStatusTrackingRepository;
import gov.naco.soch.repository.BeneficiaryRepository;
import gov.naco.soch.repository.IctcBeneficiaryRepository;
import gov.naco.soch.repository.IctcSampleBatchRepository;
import gov.naco.soch.repository.IctcSampleCollectionRepository;
import gov.naco.soch.repository.IctcTestResultRepository;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterInfantBreastFeedRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;
import gov.naco.soch.util.UserUtils;

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

	@Autowired
	private BeneficiaryFamilyDetailRepository beneficiaryFamilyDetailRepository;

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	@Autowired
	private IctcSampleBatchRepository ictcSampleBatchRepository;

	@Autowired
	private BeneficiaryIctcStatusTrackingRepository beneficiaryIctcStatusTrackingRepository;

	@Autowired
	private IctcBeneficiaryRepository ictcBeneficiaryRepository;

	public TestSamplesResponseDto fetchTestResultsList(Long labId, Integer pageNo, Integer pageSize) {

		logger.debug("In fetchTestResultsList() of TestResultService");

//		MasterSampleStatus masterSampleStatusAccepted = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
//				Boolean.FALSE);
//
//		MasterSampleStatus masterSampleStatusResultPosted = masterSampleStatusRepository
//				.findByStatusAndIsDelete("RESULT POSTED", Boolean.FALSE);
//
//		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("PENDING",
//				Boolean.FALSE);
//
//		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
//				Boolean.FALSE);
//
//		Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
//				.getId() != masterBatchStatus.getId();
//
//		Predicate<LabTestSample> isSampleInLab = s -> (s.getLabTestSampleBatch() != null)
//				&& (s.getLabTestSampleBatch().getLab().getId() == labId);
//
//		Predicate<LabTestSample> sampleStatus = s -> s.getMasterSampleStatus() != null
//				&& (s.getMasterSampleStatus().getId() == masterSampleStatusAccepted.getId()
//						|| s.getMasterSampleStatus().getId() == masterSampleStatusResultPosted.getId());
//
//		Predicate<LabTestSample> checkResultStatus = s -> s.getMasterResultStatus().getId() != masterResultStatus
//				.getId();

		TestSamplesResponseDto dto = new TestSamplesResponseDto();
		Pageable paging = PageRequest.of(pageNo, pageSize);

		List<TestResultDto> testResultDto = new ArrayList<>();
		Page<LabTestSample> labTestSampleList = labTestSampleRepository.findSamplesTestResults(labId, paging);
		if (labTestSampleList.hasContent()) {
//			labTestSampleList = labTestSampleList.stream().filter(isSampleInLab).collect(Collectors.toList());
//			labTestSampleList = labTestSampleList.stream().filter(sampleStatus).collect(Collectors.toList());
//			labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
//			labTestSampleList = labTestSampleList.stream().filter(checkResultStatus).collect(Collectors.toList());
			testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
					.collect(Collectors.toList());
			LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();
			if (FacilityTypeEnum.VL_PUBLIC.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchVLTestCount(testResultDto);
			}

			if (FacilityTypeEnum.LABORATORY_EID.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchIctcInfantDetails(testResultDto);
				findPreviousDBSDetails(testResultDto);
			}
			testResultDto = testResultDto.stream().collect(Collectors.toList());
			dto.setSamples(testResultDto);
			dto.setTotalCount(labTestSampleList.getTotalElements());
		}
		dto.setPageNumber(pageNo);
		dto.setCurrentCount(pageSize);
		return dto;
	}

	void fetchVLTestCount(List<TestResultDto> testResultDto) {
		if (!CollectionUtils.isEmpty(testResultDto)) {
			testResultDto.forEach(s -> {
				Long count = labTestSampleRepository.getVLTestCountOfBeneficiary(s.getBeneficiaryId());
				s.setVlTestCount(count);
			});
		}
	}

	public TestSamplesResponseDto fetchTestResultsUnderApproval(Long labId, Integer pageNo, Integer pageSize) {

		logger.debug("In fetchTestResultsUnderApproval() of TestResultService");

//		MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
//				Boolean.FALSE);
//
//		MasterResultStatus masterResultStatus = masterResultStatusRepository
//				.findByStatusAndIsDelete("AWAITING APPROVAL", Boolean.FALSE);
//
//		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
//				Boolean.FALSE);
//
//		Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
//				.getId() != masterBatchStatus.getId();
//
//		Predicate<LabTestSample> isSampleInLab = s -> s.getLabTestSampleBatch().getLab().getId() == labId;
//
//		Predicate<LabTestSample> statusAccepted = s -> s.getMasterSampleStatus() != null
//				&& s.getMasterSampleStatus().getId() == masterSampleStatus.getId();
//
//		Predicate<LabTestSample> checkResultStatus = s -> s.getMasterResultStatus().getId() == masterResultStatus
//				.getId();

		TestSamplesResponseDto dto = new TestSamplesResponseDto();
		Pageable paging = PageRequest.of(pageNo, pageSize);

		List<TestResultDto> testResultDto = new ArrayList<>();
		Page<LabTestSample> labTestSampleList = labTestSampleRepository.findSamplesForApproval(labId, paging);
		if (labTestSampleList.hasContent()) {
//			labTestSampleList = labTestSampleList.stream().filter(statusAccepted).collect(Collectors.toList());
//			labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
//			labTestSampleList = labTestSampleList.stream().filter(isSampleInLab.and(checkResultStatus))
//					.collect(Collectors.toList());
			testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
					.collect(Collectors.toList());
			LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();
			if (FacilityTypeEnum.VL_PUBLIC.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchVLTestCount(testResultDto);
			}

			if (FacilityTypeEnum.LABORATORY_EID.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchIctcInfantDetails(testResultDto);
				findPreviousDBSDetails(testResultDto);
			}
			testResultDto = testResultDto.stream().sorted(Comparator.comparing(TestResultDto::getBatchId).reversed())
					.collect(Collectors.toList());
			dto.setSamples(testResultDto);
			dto.setTotalCount(labTestSampleList.getTotalElements());
		}
		dto.setPageNumber(pageNo);
		dto.setCurrentCount(pageSize);
		return dto;
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

			Long facilityType = UserUtils.getLoggedInUserDetails().getFacilityTypeId();
			if (facilityType == FacilityTypeEnum.LABORATORY_EID.getFacilityType()) {
				updateIctc(labTestSampleList);
				updateIctcBeneficiaryAndStatusTracking(labTestSampleList);
			}
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

			Long facilityType = UserUtils.getLoggedInUserDetails().getFacilityTypeId();
			if (facilityType == FacilityTypeEnum.LABORATORY_EID.getFacilityType()) {
				updateIctc(labTestSampleList);
				updateIctcBeneficiaryAndStatusTracking(labTestSampleList);
			}
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

	private void changeBatchStatusICTC(List<Long> batchIds) {

		batchIds = batchIds.stream().distinct().collect(Collectors.toList());

		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("RESULT POSTED",
				Boolean.FALSE);

		List<IctcSampleBatch> labTestSampleBatchList = ictcSampleBatchRepository.findAllById(batchIds);
		if (!CollectionUtils.isEmpty(labTestSampleBatchList)) {

			labTestSampleBatchList.stream().forEach(b -> {

				Boolean accepted = Boolean.FALSE;
				int acceptCount = 0;
				if (!CollectionUtils.isEmpty(b.getSampleCollection())) {
					for (IctcSampleCollection s : b.getSampleCollection()) {
						if (s.getIctcTestResult().get(0).getResultStatus().equals(3L)) {
							acceptCount++;
						}
					}
					if (acceptCount == b.getSampleCollection().size()) {
						accepted = Boolean.TRUE;
					}
				}
				if (accepted) {
					b.setBatchStatus(5L);
				}
			});

			ictcSampleBatchRepository.saveAll(labTestSampleBatchList);
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
							samplesOpt.getVisit().setBeneficiaryStatus(11L);
							testResultOpt.setResultStatus(labTestSample.getMasterResultStatus().getId());
							testResultOpt.setTestedDate(labTestSample.getResultReceivedDate().toLocalDate());
							testResultOpt.setReportDeliveryDate(LocalDate.now());
							samplesToSave.add(samplesOpt);
							testResultToSave.add(testResultOpt);
						}
					}
				}
			});
			ictcSampleCollectionRepository.saveAll(samplesToSave);
			List<IctcTestResult> currentTestResults = ictcTestResultRepository.saveAll(testResultToSave);

			if (!CollectionUtils.isEmpty(currentTestResults)) {

				List<Beneficiary> updateBeneficiaryList = new ArrayList<Beneficiary>();

				for (IctcTestResult ictcTestResult : currentTestResults) {

					if ((ictcTestResult.getSample() != null) && (ictcTestResult.getResultStatus() == 3L)) {

						List<IctcTestResultProjection> previousTests = ictcTestResultRepository
								.findAllCDBSTestByIctcBenficiaryId(ictcTestResult.getIctcBeneficiary().getId());

						if ((!CollectionUtils.isEmpty(previousTests)) && previousTests.size() == 1) {
							if (previousTests.get(0).getHivStatus() != null
									&& previousTests.get(0).getHivStatus() == 1L) {
								Beneficiary beneficiary = updateBenficiaryHIVStatus(ictcTestResult);
								updateBeneficiaryList.add(beneficiary);
							}
						} else if ((!CollectionUtils.isEmpty(previousTests)) && previousTests.size() > 1) {

							if ((ictcTestResult.getHivStatus() != null) && (ictcTestResult.getHivStatus() == 1L)) {
								int isNegetive = 0;
								for (IctcTestResultProjection test : previousTests) {
									if (test.getHivStatus() != null && test.getHivStatus() == 1L) {
										isNegetive = isNegetive + 1;
									}
								}
								if (isNegetive > 1) {
									Beneficiary beneficiary = updateBenficiaryHIVStatus(ictcTestResult);
									updateBeneficiaryList.add(beneficiary);
								}
							} else if ((ictcTestResult.getHivStatus() != null)
									&& (ictcTestResult.getHivStatus() == 2L)) {
								int isPositive = 0;
								for (IctcTestResultProjection test : previousTests) {
									if (test.getHivStatus() != null && test.getHivStatus() == 2L) {
										isPositive = isPositive + 1;
									}
								}
								if (isPositive > 1) {
									Beneficiary beneficiary = updateBenficiaryHIVStatus(ictcTestResult);
									updateBeneficiaryList.add(beneficiary);
								}
							}

						}

					}

				}

				if (!CollectionUtils.isEmpty(updateBeneficiaryList)) {
					beneficiaryRepository.saveAll(updateBeneficiaryList);
				}

				List<Long> batchIds = samples.stream().map(s -> s.getBatch().getId()).collect(Collectors.toList());
				changeBatchStatusICTC(batchIds);
			}

		}
	}

	private Beneficiary updateBenficiaryHIVStatus(IctcTestResult ictcTest) {

		Beneficiary beneficiary = ictcTest.getIctcBeneficiary().getBeneficiary();

		if (ictcTest.getHivStatus() != null) {
			MasterHivStatus hivStatus = new MasterHivStatus();
			hivStatus.setId(ictcTest.getHivStatus());
			beneficiary.setHivStatusId(hivStatus);
		}
		if (ictcTest.getHivType() != null) {
			MasterHivType hivType = new MasterHivType();
			hivType.setId(ictcTest.getHivType());
			beneficiary.setHivTypeId(hivType);
		}

		return beneficiary;
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
									+ (StringUtils.isEmpty(motherAddress.getAddressLineTwo()) ? ""
											: ", " + motherAddress.getAddressLineTwo());
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
	}

	void findPreviousDBSDetails(List<TestResultDto> testDetails) {

		LoginResponseDto userLoginDetials = UserUtils.getLoggedInUserDetails();
		if (userLoginDetials.getFacilityTypeId() == 20L) {

			for (TestResultDto s : testDetails) {

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

	public TestSamplesResponseDto getTestResultsListByAdvanceSearch(Long labId, Map<String, String> searchValue) {

//		MasterSampleStatus masterSampleStatusAccepted = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
//				Boolean.FALSE);
//
//		MasterSampleStatus masterSampleStatusResultPosted = masterSampleStatusRepository
//				.findByStatusAndIsDelete("RESULT POSTED", Boolean.FALSE);
//
//		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("PENDING",
//				Boolean.FALSE);
//
//		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
//				Boolean.FALSE);
//
//		Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
//				.getId() != masterBatchStatus.getId();
//
//		Predicate<LabTestSample> isSampleInLab = s -> s.getLabTestSampleBatch().getLab().getId() == labId;
//
//		Predicate<LabTestSample> sampleStatus = s -> s.getMasterSampleStatus() != null
//				&& (s.getMasterSampleStatus().getId() == masterSampleStatusAccepted.getId()
//						|| s.getMasterSampleStatus().getId() == masterSampleStatusResultPosted.getId());
//
//		Predicate<LabTestSample> checkResultStatus = s -> s.getMasterResultStatus().getId() != masterResultStatus
//				.getId();

		TestSamplesResponseDto dto = new TestSamplesResponseDto();

		List<TestResultDto> testResultDto = new ArrayList<>();
		List<String> searchQuery = AdvanceSearchMapperUtil.queryCreaterForAdvanceSearchResultsList(labId, searchValue,
				Boolean.TRUE, Boolean.FALSE);
		if (!searchQuery.isEmpty()) {
			List<LabTestSample> labTestSampleList = labTestSampleRepository
					.getRecordResultsListByAdvanceSearch(searchQuery.get(0));
			if (!CollectionUtils.isEmpty(labTestSampleList)) {
//				labTestSampleList = labTestSampleList.stream().filter(sampleStatus).collect(Collectors.toList());
//				labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
//				labTestSampleList = labTestSampleList.stream().filter(isSampleInLab.and(checkResultStatus))
//						.collect(Collectors.toList());
				testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
						.collect(Collectors.toList());
				LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();
				if (FacilityTypeEnum.VL_PUBLIC.getFacilityType().equals(currentUser.getFacilityTypeId())) {
					fetchVLTestCount(testResultDto);
				}

				if (FacilityTypeEnum.LABORATORY_EID.getFacilityType().equals(currentUser.getFacilityTypeId())) {
					fetchIctcInfantDetails(testResultDto);
					findPreviousDBSDetails(testResultDto);
				}

				testResultDto = testResultDto.stream()
						.sorted(Comparator.comparing(TestResultDto::getBatchId).reversed())
						.collect(Collectors.toList());
				dto.setSamples(testResultDto);
				dto.setTotalCount((long) labTestSampleList.size());
			}
		}
		return dto;
	}

	public TestSamplesResponseDto getRecordResultsUnderApprovalAdvanceSearch(Long labId,
			Map<String, String> searchValue) {

//		MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
//				Boolean.FALSE);
//
//		MasterResultStatus masterResultStatus = masterResultStatusRepository
//				.findByStatusAndIsDelete("AWAITING APPROVAL", Boolean.FALSE);
//
//		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
//				Boolean.FALSE);
//
//		Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
//				.getId() != masterBatchStatus.getId();
//
//		Predicate<LabTestSample> isSampleInLab = s -> s.getLabTestSampleBatch().getLab().getId() == labId;
//
//		Predicate<LabTestSample> statusAccepted = s -> s.getMasterSampleStatus() != null
//				&& s.getMasterSampleStatus().getId() == masterSampleStatus.getId();
//
//		Predicate<LabTestSample> checkResultStatus = s -> s.getMasterResultStatus().getId() == masterResultStatus
//				.getId();

		TestSamplesResponseDto dto = new TestSamplesResponseDto();

		List<TestResultDto> testResultDto = new ArrayList<>();
		List<String> searchQuery = AdvanceSearchMapperUtil.queryCreaterForAdvanceSearchResultsList(labId, searchValue,
				Boolean.FALSE, Boolean.TRUE);
		if (!searchQuery.isEmpty()) {
			List<LabTestSample> labTestSampleList = labTestSampleRepository
					.getRecordResultsListByAdvanceSearch(searchQuery.get(0));
			if (!CollectionUtils.isEmpty(labTestSampleList)) {
//				labTestSampleList = labTestSampleList.stream().filter(statusAccepted).collect(Collectors.toList());
//				labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
//				labTestSampleList = labTestSampleList.stream().filter(isSampleInLab.and(checkResultStatus))
//						.collect(Collectors.toList());
				testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
						.collect(Collectors.toList());
				LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();
				if (FacilityTypeEnum.VL_PUBLIC.getFacilityType().equals(currentUser.getFacilityTypeId())) {
					fetchVLTestCount(testResultDto);
				}

				if (FacilityTypeEnum.LABORATORY_EID.getFacilityType().equals(currentUser.getFacilityTypeId())) {
					fetchIctcInfantDetails(testResultDto);
					findPreviousDBSDetails(testResultDto);
				}
				testResultDto = testResultDto.stream()
						.sorted(Comparator.comparing(TestResultDto::getBatchId).reversed())
						.collect(Collectors.toList());
				dto.setSamples(testResultDto);
				dto.setTotalCount((long) testResultDto.size());
			}

		}
		return dto;
	}

	public void updateIctcBeneficiaryAndStatusTracking(List<LabTestSample> labTestSampleList) {
		List<BeneficiaryIctcStatusTracking> trackingList = new ArrayList<BeneficiaryIctcStatusTracking>();
		if (labTestSampleList != null) {
			for (LabTestSample sample : labTestSampleList) {
				BeneficiaryIctcStatusTracking status = beneficiaryIctcStatusTrackingRepository
						.getPreviousStatus(sample.getBeneficiary().getId());
				trackingList.add(TestResultMapper.mappingStatuses(sample, status.getCurrentIctcBeneficiaryStatusId()));

			}
			if (!CollectionUtils.isEmpty(trackingList)) {
				beneficiaryIctcStatusTrackingRepository.saveAll(trackingList);
				for (BeneficiaryIctcStatusTracking track : trackingList) {
					ictcBeneficiaryRepository.updateBeneficiaryStatus(track.getCurrentIctcBeneficiaryStatusId(),
							track.getBeneficiaryId());
				}
			}
		}
	}

	public TestSamplesResponseDto fetchTestResultsListByNormalSearch(Long labId, String searchValue, Integer pageNo,
			Integer pageSize) {
		TestSamplesResponseDto dto = new TestSamplesResponseDto();
		Pageable paging = PageRequest.of(pageNo, pageSize);
		searchValue = '%' + searchValue.trim() + '%';
		List<TestResultDto> testResultDto = new ArrayList<>();
		Page<LabTestSample> labTestSampleList = labTestSampleRepository.fetchTestResultsListByNormalSearch(labId,
				searchValue, paging);
		if (labTestSampleList.hasContent()) {
			testResultDto = labTestSampleList.stream().map(s -> TestResultMapper.mapToTestResultDto(s))
					.collect(Collectors.toList());
			LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();
			if (FacilityTypeEnum.VL_PUBLIC.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchVLTestCount(testResultDto);
			}

			if (FacilityTypeEnum.LABORATORY_EID.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchIctcInfantDetails(testResultDto);
				findPreviousDBSDetails(testResultDto);
			}
			testResultDto = testResultDto.stream().collect(Collectors.toList());
			dto.setSamples(testResultDto);
			dto.setTotalCount(labTestSampleList.getTotalElements());
		}
		dto.setPageNumber(pageNo);
		dto.setCurrentCount(pageSize);
		return dto;
	}

}
