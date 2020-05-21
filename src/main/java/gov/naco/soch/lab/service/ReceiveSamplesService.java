package gov.naco.soch.lab.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.IctcSampleCollection;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterInfantBreastFeed;
import gov.naco.soch.entity.MasterRemark;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.mapper.ReceiveSamplesServiceMapperUtil;
import gov.naco.soch.repository.IctcSampleCollectionRepository;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterInfantBreastFeedRepository;
import gov.naco.soch.repository.MasterRemarkRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;

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
		}
		return labTestSampleBatchDtoList;
	}

	public LabTestSampleBatchDto saveReceivedSamples(Long batchId, LabTestSampleBatchDto labTestSampleBatchDto) {

		Optional<LabTestSampleBatch> labTestSampleBatchOpt = labTestSampleBatchRepository.findById(batchId);
		if (labTestSampleBatchOpt.isPresent()) {
			LabTestSampleBatch labTestSampleBatch = labTestSampleBatchOpt.get();
			labTestSampleBatch.setAcceptedSamples(labTestSampleBatchDto.getAcceptedSamples());
			labTestSampleBatch.setRejectedSamples(labTestSampleBatchDto.getRejectedSamples());
			labTestSampleBatch.setReceivedDate(LocalDateTime.now());
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

						if (sample.getSampleStatusId() != null && sample.getSampleStatusId() != 0L) {
							Optional<MasterSampleStatus> sampleStatusOpt = masterSampleStatusRepository
									.findById(sample.getSampleStatusId());
							if (sampleStatusOpt.isPresent()) {
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
