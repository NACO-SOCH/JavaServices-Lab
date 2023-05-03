package gov.naco.soch.lab.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDateTime;
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
import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.IctcSampleBatch;
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
import gov.naco.soch.enums.FacilityTypeEnum;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.dto.ReceiceSamplesResponseDto;
import gov.naco.soch.lab.mapper.AdvanceSearchMapperUtil;
import gov.naco.soch.lab.mapper.ReceiveSamplesServiceMapperUtil;
import gov.naco.soch.projection.FacilityProjection;
import gov.naco.soch.projection.LabTestReceiveBatchProjection;
import gov.naco.soch.repository.BeneficiaryFamilyDetailRepository;
import gov.naco.soch.repository.FacilityRepository;
import gov.naco.soch.repository.IctcSampleBatchRepository;
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
	private IctcSampleBatchRepository ictcSampleBatchRepository;

	@Autowired
	private BeneficiaryFamilyDetailRepository beneficiaryFamilyDetailRepository;

	@Autowired
	private FacilityRepository facilityRepository;

	private static final Logger logger = LoggerFactory.getLogger(ReceiveSamplesService.class);

//	public List<LabTestSampleBatchDto> fetchReceiveSamplesList(Long labId) {
//		logger.debug("In fetchReceiveSamplesList() of RecieveSamplesService");
//
//		List<LabTestSampleBatchDto> labTestSampleBatchDtoList = new ArrayList<>();
//
//		List<LabTestSampleBatch> labTestSampleBatchList = labTestSampleBatchRepository.findByLabIdAndIsDelete(labId,
//				Boolean.FALSE);
//		if (!CollectionUtils.isEmpty(labTestSampleBatchList)) {
//
//			labTestSampleBatchList.forEach(l -> {
//				LabTestSampleBatchDto labTestSampleBatchDto = ReceiveSamplesServiceMapperUtil
//						.mapToLabTestSampleBatchDto(l);
//				labTestSampleBatchDtoList.add(labTestSampleBatchDto);
//			});
//			fetchVLTestCount(labTestSampleBatchDtoList);
//			fetchIctcInfantDetails(labTestSampleBatchDtoList);	
//			findPreviousDBSDetails(labTestSampleBatchDtoList);
//		}
//		return labTestSampleBatchDtoList.stream()
//				.sorted(Comparator.comparing(LabTestSampleBatchDto::getBatchId).reversed())
//				.collect(Collectors.toList());
//	}

	public ReceiceSamplesResponseDto fetchReceiveSamplesList(Long labId, Integer pageNo, Integer pageSize) {
		logger.debug("In fetchReceiveSamplesList() of RecieveSamplesService");

		ReceiceSamplesResponseDto dto = new ReceiceSamplesResponseDto();

		Pageable paging = PageRequest.of(pageNo, pageSize);

		Page<LabTestReceiveBatchProjection> labTestSampleBatchList = labTestSampleBatchRepository
				.findTestBatchesByLabId(labId, paging);

		if (labTestSampleBatchList.hasContent()) {

			List<LabTestSampleBatchDto> labTestSampleBatchDtoList = new ArrayList<>();

			List<Long> batchIds = labTestSampleBatchList.stream().map(l -> l.getBatchId()).collect(Collectors.toList());

			List<LabTestReceiveBatchProjection> labTestSampleList = labTestSampleBatchRepository
					.findTestSamplesInBatches(batchIds);
			Map<Long, List<LabTestReceiveBatchProjection>> labTestSampleMap = labTestSampleList.stream()
					.collect(Collectors.groupingBy(LabTestReceiveBatchProjection::getBatchId));

			for (LabTestReceiveBatchProjection b : labTestSampleBatchList) {
				LabTestSampleBatchDto labTestSampleBatchDto = ReceiveSamplesServiceMapperUtil
						.mapBatchProjectionToLabTestSampleBatchDto(b, labTestSampleMap.get(b.getBatchId()));
				labTestSampleBatchDtoList.add(labTestSampleBatchDto);
			}

			List<MasterBatchStatus> batchStatus = masterBatchStatusRepository.findByIsDelete(false);
			Map<Long, String> batchStatusMap = batchStatus.stream()
					.collect(Collectors.toMap(MasterBatchStatus::getId, MasterBatchStatus::getStatus));

			List<Long> facilityIds = labTestSampleBatchDtoList.stream().map(l -> l.getArtcId()).filter(i -> i != null)
					.distinct().collect(Collectors.toList());
			List<FacilityProjection> facilityDetails = facilityRepository.findFacilityNameByIds(facilityIds);
			Map<Long, String> facilityDetailsMap = facilityDetails.stream()
					.collect(Collectors.toMap(FacilityProjection::getId, FacilityProjection::getName));

			labTestSampleBatchDtoList.forEach(b -> {
				b.setBatchStatus(batchStatusMap.get(b.getBatchStatusId()));
				if (b.getArtcId() != null) {
					b.setArtcName(facilityDetailsMap.get(b.getArtcId()));
				}
			});

			labTestSampleBatchDtoList = labTestSampleBatchDtoList.stream()
					.sorted(Comparator.comparing(LabTestSampleBatchDto::getBatchId).reversed())
					.collect(Collectors.toList());
			dto.setBatches(labTestSampleBatchDtoList);
			dto.setTotalCount(labTestSampleBatchList.getTotalElements());
		}
		dto.setCurrentCount(pageSize);
		dto.setPageNumber(pageNo);
		return dto;
	}

	void fetchVLTestCount(List<LabTestSampleBatchDto> labTestSampleBatchDtoList) {
		if (!CollectionUtils.isEmpty(labTestSampleBatchDtoList)) {
			labTestSampleBatchDtoList.forEach(b -> {
				if (!CollectionUtils.isEmpty(b.getLabTestSampleDtoList())) {
					b.getLabTestSampleDtoList().forEach(s -> {
						Long count = labTestSampleRepository.getVLTestCountOfBeneficiary(s.getBeneficiaryId());
						s.setVlTestCount(count);
					});
				}
			});

		}
	}

	public LabTestSampleBatchDto saveReceivedSamples(Long batchId, LabTestSampleBatchDto labTestSampleBatchDto) {

		Optional<LabTestSampleBatch> labTestSampleBatchOpt = labTestSampleBatchRepository.findById(batchId);
		Long facilityType = UserUtils.getLoggedInUserDetails().getFacilityTypeId();
		
		if (labTestSampleBatchOpt.isPresent()) {
			LabTestSampleBatch labTestSampleBatch = labTestSampleBatchOpt.get();
			labTestSampleBatch.setAcceptedSamples(labTestSampleBatchDto.getAcceptedSamples());
			labTestSampleBatch.setRejectedSamples(labTestSampleBatchDto.getRejectedSamples());
			if (facilityType == FacilityTypeEnum.LABORATORY_EID.getFacilityType()) {
				labTestSampleBatch.setReceivedDate(LocalDateTime.now());
			}else {
				labTestSampleBatch.setReceivedDate(labTestSampleBatchDto.getReceivedDate());
			}
			
		
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
							.filter(ts -> ts.getSampleId().equals(s.getId())).findFirst();
					if (sampleOpt.isPresent()) {
						LabTestSampleDto sample = sampleOpt.get();
						s.setLabTecnician(labTechUserOpt.get());
						if (sample.getSampleStatusId() != null && sample.getSampleStatusId() != 0L) {
							Optional<MasterSampleStatus> sampleStatusOpt = masterSampleStatusRepository
									.findById(sample.getSampleStatusId());
							if (sampleStatusOpt.isPresent()) {
								
								if (facilityType == FacilityTypeEnum.LABORATORY_EID.getFacilityType()) {
								 s.setSampleReceivedDate(LocalDateTime.now());
								}else {
								 s.setSampleReceivedDate(labTestSampleBatchDto.getReceivedDate());
								}
								 
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
							s.setSampleReceivedDate(labSampleOpt.get().getSampleReceivedDate());
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

	public ReceiceSamplesResponseDto getReceiveSamplesListByAdvanceSearch(Long labId, Map<String, String> searchValue) {
		ReceiceSamplesResponseDto dto = new ReceiceSamplesResponseDto();
		List<LabTestSampleBatchDto> labTestSampleBatchDtoList = new ArrayList<>();
		List<String> searchQuery = AdvanceSearchMapperUtil.queryCreaterForAdvanceSearchReceiveSampleList(labId,
				searchValue);
		if (!searchQuery.isEmpty()) {
			List<LabTestSampleBatch> labTestSampleBatchList = labTestSampleBatchRepository
					.getReceiveSamplesListByAdvanceSearch(searchQuery.get(0));
			if (!CollectionUtils.isEmpty(labTestSampleBatchList)) {

				for (LabTestSampleBatch l : labTestSampleBatchList) {
					LabTestSampleBatchDto labTestSampleBatchDto = ReceiveSamplesServiceMapperUtil
							.mapToLabTestSampleBatchDto(l);
					labTestSampleBatchDtoList.add(labTestSampleBatchDto);
				}

				labTestSampleBatchDtoList = labTestSampleBatchDtoList.stream()
						.sorted(Comparator.comparing(LabTestSampleBatchDto::getBatchId).reversed())
						.collect(Collectors.toList());

				dto.setBatches(labTestSampleBatchDtoList);
				dto.setTotalCount((long) labTestSampleBatchDtoList.size());
//				fetchVLTestCount(labTestSampleBatchDtoList);
//				fetchIctcInfantDetails(labTestSampleBatchDtoList);
//				findPreviousDBSDetails(labTestSampleBatchDtoList);
			}
		}
		return dto;
	}

	public void undoDispatchedSample(Long batchId) {

		Optional<LabTestSampleBatch> labTestSampleBatchOpt = labTestSampleBatchRepository.findById(batchId);
		if (labTestSampleBatchOpt.isPresent()) {

			LabTestSampleBatch batch = labTestSampleBatchOpt.get();
			List<LabTestSample> samples = labTestSampleRepository.findSamplesByBatchId(batch.getId());
			if (!CollectionUtils.isEmpty(samples)) {
				samples = samples.stream().map(s -> {
					s.setLabTestSampleBatch(null);
					s.setDispatchedToLab(null);
					s.setSampleDispatchDate(null);
					s.setMasterSampleStatus(null);
					s.setArtcSampleStatus("Sample Collected");
					s.setIsUndone(Boolean.TRUE);
					return s;
				}).collect(Collectors.toList());

				labTestSampleRepository.saveAll(samples);
			}
			labTestSampleBatchRepository.delete(batch);

			LoginResponseDto currentLoginDetils = UserUtils.getLoggedInUserDetails();
			if (currentLoginDetils.getFacilityTypeId().equals(20L)) {
				undoDispatchIctcSamples(samples);
			}

		} else {
			throw new ServiceException("Invalid Batch Id", null, HttpStatus.BAD_REQUEST);
		}
	}

	void undoDispatchIctcSamples(List<LabTestSample> samples) {

		List<String> barcodes = samples.stream().map(s -> s.getBarcodeNumber()).collect(Collectors.toList());

		List<IctcSampleCollection> ictcSamples = ictcSampleCollectionRepository.findBySampleBatchBarcodes(barcodes);
		if (!CollectionUtils.isEmpty(ictcSamples)) {

			Optional<IctcSampleBatch> batch = ictcSamples.stream().map(s -> s.getBatch()).distinct().findFirst();

			if (!CollectionUtils.isEmpty(ictcSamples)) {
				ictcSamples = ictcSamples.stream().map(s -> {
					s.setBatch(null);
					s.setSampleCollectionStatus(1L);
					return s;
				}).collect(Collectors.toList());

				ictcSampleCollectionRepository.saveAll(ictcSamples);
			}

			if (batch.isPresent()) {
				ictcSampleBatchRepository.delete(batch.get());
			}
		}
	}

	public LabTestSampleBatchDto fetchReceiveSamplesByBatchId(Long batchId) {
		logger.debug("In fetchReceiveSamplesList() of RecieveSamplesService");
		LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();
		LabTestSampleBatchDto labTestSampleBatchDto = new LabTestSampleBatchDto();

		Optional<LabTestSampleBatch> labTestSampleBatchOpt = labTestSampleBatchRepository.findById(batchId);

		if (labTestSampleBatchOpt.isPresent()) {

			labTestSampleBatchDto = ReceiveSamplesServiceMapperUtil
					.mapToLabTestSampleBatchDto(labTestSampleBatchOpt.get());

			List<LabTestSampleBatchDto> labTestSampleBatchDtoList = new ArrayList<>();
			labTestSampleBatchDtoList.add(labTestSampleBatchDto);

			if (FacilityTypeEnum.VL_PUBLIC.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchVLTestCount(labTestSampleBatchDtoList);
			}

			if (FacilityTypeEnum.LABORATORY_EID.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchIctcInfantDetails(labTestSampleBatchDtoList);
				findPreviousDBSDetails(labTestSampleBatchDtoList);
			}

			return labTestSampleBatchDto;
		} else {
			throw new ServiceException("Invalid Batch Id", null, HttpStatus.BAD_REQUEST);
		}
	}
}
