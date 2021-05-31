package gov.naco.soch.lab.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import gov.naco.soch.dto.ErrorResponse;
import gov.naco.soch.dto.LoginResponseDto;
import gov.naco.soch.entity.Address;
import gov.naco.soch.entity.Beneficiary;
import gov.naco.soch.entity.BeneficiaryFamilyDetail;
import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.IctcSampleCollection;
import gov.naco.soch.entity.IctcTestResult;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterInfantBreastFeed;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterResultType;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.enums.FacilityTypeEnum;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.RecordBatchResultDto;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.dto.TestSamplesResponseDto;
import gov.naco.soch.lab.mapper.AdvanceSearchMapperUtil;
import gov.naco.soch.lab.mapper.TestResultMapper;
import gov.naco.soch.repository.BeneficiaryFamilyDetailRepository;
import gov.naco.soch.repository.IctcSampleCollectionRepository;
import gov.naco.soch.repository.IctcTestResultRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterInfantBreastFeedRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterResultTypeRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;
import gov.naco.soch.util.UserUtils;

@Service
@Transactional
public class RecordResultsService {

	private static String RECIEVED = "RECIEVED";

	private static String REJECTED = "REJECTED";

	private static String PARTIALLY_RECEIVED = "PARTIALLY RECEIVED";

	private static String RESULT_POSTED = "RESULT POSTED";

	private static final Logger logger = LoggerFactory.getLogger(TestResultService.class);

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	@Value("${lab.recordresult.backdate.enabled}")
	private Boolean recordresultBackdateEnabled;

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private MasterSampleStatusRepository masterSampleStatusRepository;

	@Autowired
	private MasterResultStatusRepository masterResultStatusRepository;

	@Autowired
	private MasterResultTypeRepository masterResultTypeRepository;

	@Autowired
	private MasterBatchStatusRepository masterBatchStatusRepository;

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Autowired
	private IctcSampleCollectionRepository ictcSampleCollectionRepository;

	@Autowired
	private MasterInfantBreastFeedRepository masterInfantBreastFeedRepository;

	@Autowired
	private IctcTestResultRepository ictcTestResultRepository;

	@Autowired
	private BeneficiaryFamilyDetailRepository beneficiaryFamilyDetailRepository;

	@Autowired
	private TestResultService testResultService;

	public TestSamplesResponseDto getRecordResultsList(Long labId, Integer pageNo, Integer pageSize) {

		logger.debug("In getRecordResultsList() of RecordResultsService");

		TestSamplesResponseDto dto = new TestSamplesResponseDto();
		Pageable paging = PageRequest.of(pageNo, pageSize);

		List<TestResultDto> testResultDto = new ArrayList<>();
		Page<LabTestSample> labTestSampleList = labTestSampleRepository.findSamplesToRecordResult(labId, paging);
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

			testResultDto = testResultDto.stream().sorted(Comparator.comparing(TestResultDto::getBatchId).reversed())
					.collect(Collectors.toList());
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

	public TestResultDto saveRecordResult(Long sampleId, TestResultDto labTestSampleDto) {

		logger.debug("In getRecordResultsList() of RecordResultsService");

		Optional<LabTestSample> labTestSampleOpt = labTestSampleRepository.findById(sampleId);

		MasterResultStatus masterResultStatus = masterResultStatusRepository
				.findByStatusAndIsDelete("AWAITING APPROVAL", Boolean.FALSE);

		if (labTestSampleOpt.isPresent()) {

			LabTestSample labTestSample = labTestSampleOpt.get();
			if (labTestSampleDto.getResultReceivedDate() != null) {
				LocalDateTime currentTime = LocalDateTime.now();
				labTestSample
						.setResultReceivedDate(LocalDateTime.parse(labTestSampleDto.getResultReceivedDate(), formatter)
								.withHour(currentTime.getHour()).withMinute(currentTime.getMinute()));
			}

			Optional<UserMaster> labTechUserOpt = userMasterRepository.findById(labTestSampleDto.getLabTechnicianId());
			if (labTechUserOpt.isPresent()) {
				labTestSample.setLabTecnician(labTechUserOpt.get());
			} else {
				throw new ServiceException("Invalid User", null, HttpStatus.BAD_REQUEST);
			}

			labTestSample.setResultValue(labTestSampleDto.getResultValue());
			labTestSample.setLogValue(labTestSampleDto.getLogValue());
			labTestSample.setIsError(labTestSampleDto.getIsError());
			labTestSample.setErrorCode(labTestSampleDto.getErrorCode());
			labTestSample.setMasterResultStatus(masterResultStatus);

			if (labTestSampleDto.getResultTypeId() != null) {
				Optional<MasterResultType> resultTypeOpt = masterResultTypeRepository
						.findById(labTestSampleDto.getResultTypeId());
				if (resultTypeOpt.isPresent()) {
					labTestSample.setResultType(resultTypeOpt.get());
				}
			}

			// Block to handle case of MHL Labs
			if (labTestSample.getDispatchedToLab().getFacilityType().getId() == FacilityTypeEnum.VL_PRIVATE
					.getFacilityType()) {
				handleMHLFacilityTest(labTestSample, labTestSampleDto);
			}

			labTestSample = labTestSampleRepository.save(labTestSample);

			Long facilityType = UserUtils.getLoggedInUserDetails().getFacilityTypeId();
			if (facilityType == FacilityTypeEnum.LABORATORY_EID.getFacilityType()) {
				updateIctc(labTestSample);
				List<LabTestSample> sampleList = new ArrayList<LabTestSample>();
				sampleList.add(labTestSample);
				testResultService.updateIctcBeneficiaryAndStatusTracking(sampleList);
			}
			return TestResultMapper.mapToTestResultDto(labTestSample);
		} else {
			throw new ServiceException("SampleID not present", null, HttpStatus.BAD_REQUEST);
		}
	}

	public List<TestResultDto> getRecordResultsArtcList(Long artcId) {

		logger.debug("In getRecordResultsArtcList() of RecordResultsService");

		MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
				Boolean.FALSE);

		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("PENDING",
				Boolean.FALSE);

		MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
				Boolean.FALSE);

		Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
				.getId() != masterBatchStatus.getId();

		Predicate<LabTestSample> isSampleInLab = s -> s.getLabTestSampleBatch().getFacility().getId() == artcId;

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
		return testResultDto;
	}

	private void updateIctc(LabTestSample labTestSample) {
		Facility facility = labTestSample.getLabTestSampleBatch().getFacility();
		if (facility != null && facility.getFacilityType() != null
				&& (facility.getFacilityType().getId() == 11L || facility.getFacilityType().getId() == 13L)) {

			Optional<IctcSampleCollection> samplesOpt = ictcSampleCollectionRepository
					.findByBarcode(labTestSample.getBarcodeNumber());

			if (samplesOpt.isPresent()) {

				IctcSampleCollection sample = samplesOpt.get();
				Optional<IctcTestResult> testResultOpt = ictcTestResultRepository.findBySampleId(sample.getId());

				if (testResultOpt.isPresent()) {
					IctcTestResult testResult = testResultOpt.get();
					if (labTestSample.getResultType() != null) {

						if (labTestSample.getResultType().getId() == 6L) {
							testResult.setHivStatus(2L);
						}
						if (labTestSample.getResultType().getId() == 7L) {
							testResult.setHivStatus(1L);
						}
						if (labTestSample.getResultType().getId() == 8L) {
							testResult.setHivStatus(3L);
						}
					}
					testResult.setResultStatus(labTestSample.getMasterResultStatus().getId());
					testResult.setTestedDate(labTestSample.getResultReceivedDate().toLocalDate());
					testResult.setReportReceivedDate(labTestSample.getResultReceivedDate().toLocalDate());
					ictcTestResultRepository.save(testResult);
				}
			}

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

	private void handleMHLFacilityTest(LabTestSample labTestSample, TestResultDto labTestSampleDto) {

		LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();

		UserMaster user = new UserMaster();
		user.setId(currentUser.getUserId());
		labTestSample.setLabInCharge(user);

		labTestSample.getLabTestSampleBatch().setVlLabTechUser(user);

		MasterSampleStatus sampleStatus = new MasterSampleStatus();
		MasterResultStatus resultStatus = new MasterResultStatus();

		if (labTestSampleDto.getResultTypeId() != null) {
			if (labTestSampleDto.getResultTypeId() == 11L) {
				sampleStatus.setId(4L);
				resultStatus.setId(3L);
				labTestSample.setArtcSampleStatus(RESULT_POSTED);
			}
			if (labTestSampleDto.getResultTypeId() == 12L) {
				sampleStatus.setId(4L);
				resultStatus.setId(3L);
				labTestSample.setArtcSampleStatus(RESULT_POSTED);
			}
			if (labTestSampleDto.getResultTypeId() == 13L) {
				sampleStatus.setId(4L);
				resultStatus.setId(3L);
				labTestSample.setArtcSampleStatus(RESULT_POSTED);
			}
			if (labTestSampleDto.getResultTypeId() == 14L) {
				sampleStatus.setId(4L);
				resultStatus.setId(3L);
				labTestSample.setResultValue(labTestSampleDto.getResultValue());
				labTestSample.setLogValue(labTestSampleDto.getLogValue());
				labTestSample.setArtcSampleStatus(RESULT_POSTED);
			}
			if (labTestSampleDto.getResultTypeId() == 15L) {
				sampleStatus.setId(2L);
				resultStatus.setId(5L);
				labTestSample.setArtcSampleStatus(REJECTED);
			}
			if (labTestSampleDto.getResultTypeId() == 16L) {
				sampleStatus.setId(2L);
				resultStatus.setId(5L);
				labTestSample.setArtcSampleStatus(REJECTED);
			}
		}

		labTestSample.setMasterSampleStatus(sampleStatus);
		labTestSample.setMasterResultStatus(resultStatus);

		LocalDateTime currentTime = LocalDateTime.now();
		labTestSample.setSampleReceivedDate(currentTime);
		labTestSample.setResultApprovedDate(currentTime);
		labTestSample.setResultDispatchDate(currentTime);

		changeBatchStatus(labTestSample);
	}

	private void changeBatchStatus(LabTestSample labTestSample) {

		LabTestSampleBatch labTestSampleBatchList = labTestSample.getLabTestSampleBatch();

		if (labTestSampleBatchList != null) {

			String batchStatus = findBatchStatus(labTestSampleBatchList.getLabTestSamples());
			MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete(batchStatus,
					Boolean.FALSE);

			if (labTestSampleBatchList.getReceivedDate() == null) {
				labTestSampleBatchList.setReceivedDate(LocalDateTime.now());
			}

			Boolean accepted = Boolean.FALSE;
			int acceptCount = 0;
			if (!CollectionUtils.isEmpty(labTestSampleBatchList.getLabTestSamples())) {
				for (LabTestSample s : labTestSampleBatchList.getLabTestSamples()) {
					if (s.getMasterResultStatus().getId() == 3L) {
						acceptCount++;
					}
				}
				if (acceptCount == labTestSampleBatchList.getLabTestSamples().size()) {
					accepted = Boolean.TRUE;
				}
			}
			labTestSample.getLabTestSampleBatch().setAcceptedSamples(Long.valueOf(acceptCount));
			if (accepted) {
				masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete(RESULT_POSTED, Boolean.FALSE);
				labTestSample.getLabTestSampleBatch().setMasterBatchStatus(masterBatchStatus);
			} else {
				labTestSample.getLabTestSampleBatch().setMasterBatchStatus(masterBatchStatus);
			}
		}
	}

	private String findBatchStatus(Set<LabTestSample> labTestSamples) {
		int acceptCount = 0;
		int rejectCount = 0;
		int notRecievedCount = 0;
		int samplesCount = labTestSamples.size();
		for (LabTestSample s : labTestSamples) {
			if (s.getMasterSampleStatus() != null) {
				if ((s.getMasterSampleStatus().getId() == 1L || s.getMasterSampleStatus().getId() == 4L)) {
					acceptCount++;
				}
			}
			if (s.getMasterSampleStatus() != null) {
				if (s.getMasterSampleStatus().getId() == 2L) {
					rejectCount++;
				}
			}
			if (s.getMasterSampleStatus() != null) {
				if (s.getMasterSampleStatus().getId() == 3L) {
					notRecievedCount++;
				}
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

	public TestSamplesResponseDto getRecordResultsListByAdvanceSearch(Long labId, Map<String, String> searchValue) {

		TestSamplesResponseDto dto = new TestSamplesResponseDto();

		List<TestResultDto> testResultDto = new ArrayList<>();
		List<String> searchQuery = AdvanceSearchMapperUtil.queryCreaterForAdvanceSearchRecordResultsList(labId,
				searchValue);
		if (!searchQuery.isEmpty()) {

			List<LabTestSample> labTestSampleList = labTestSampleRepository
					.getRecordResultsListByAdvanceSearch(searchQuery.get(0));
			if (!CollectionUtils.isEmpty(labTestSampleList)) {
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

	public List<RecordBatchResultDto> recordResultFromFile(@Valid List<RecordBatchResultDto> results) {

		if (!CollectionUtils.isEmpty(results)) {

			LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();

			List<LabTestSample> currentRecordResultList = labTestSampleRepository
					.findAllSamplesToRecordResult(currentUser.getFacilityId());

			if (CollectionUtils.isEmpty(currentRecordResultList)) {
				throw new ServiceException(null, new ErrorResponse("No sample to upload results", null),
						HttpStatus.NOT_FOUND);
			} else {

				MasterResultStatus masterResultStatus = masterResultStatusRepository
						.findByStatusAndIsDelete("AWAITING APPROVAL", Boolean.FALSE);

				Optional<UserMaster> labTechUserOpt = userMasterRepository.findById(currentUser.getUserId());
				if (!labTechUserOpt.isPresent()) {
					throw new ServiceException("Invalid User", null, HttpStatus.BAD_REQUEST);
				}

				List<LabTestSample> samplesToUpdate = new ArrayList<>();
				LocalDateTime currentTime = LocalDateTime.now();

				for (RecordBatchResultDto result : results) {

					Optional<LabTestSample> sampleOpt = currentRecordResultList.stream()
							.filter(r -> r.getBarcodeNumber().equals(result.getBarcode())).findFirst();

					if (sampleOpt.isPresent()) {

						LabTestSample sample = sampleOpt.get();
						Long resultTypeId = 0L;

						if (!StringUtils.isEmpty(result.getResultValue())) {

							MasterResultType resultType = new MasterResultType();

							if (result.getResultValue().equalsIgnoreCase("Not detected")) {
								resultTypeId = 2L;
							} else if (result.getResultValue().equalsIgnoreCase("< 150")) {
								resultTypeId = 1L;
							} else if (result.getResultValue().equalsIgnoreCase("> 10000000")) {
								resultTypeId = 3L;
							} else {
								resultTypeId = 4L;
								sample.setResultValue(result.getResultValue());
								Double resultVal = Double.parseDouble(result.getResultValue());
								Double logValue = Math.log(resultVal);
								if (!logValue.isNaN()) {
									sample.setLogValue(logValue.toString());
								}
							}

							resultType.setId(resultTypeId);
							sample.setResultType(resultType);
							sample.setMasterResultStatus(masterResultStatus);
							sample.setResultReceivedDate(LocalDateTime.parse(result.getResultDate(), formatter)
									.withHour(currentTime.getHour()).withMinute(currentTime.getMinute()));
							samplesToUpdate.add(sample);

							result.setIsResultRecorded(Boolean.TRUE);
						}
					}
				}

				if (!CollectionUtils.isEmpty(samplesToUpdate)) {
					labTestSampleRepository.saveAll(samplesToUpdate);
				}
			}

		} else {
			throw new ServiceException(null, new ErrorResponse("No data to upload", null), HttpStatus.BAD_REQUEST);
		}

		return results;
	}

	public Boolean getBackdatedRecordResultStatus() {
		return recordresultBackdateEnabled;
	}
}