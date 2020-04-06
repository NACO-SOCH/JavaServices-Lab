package gov.naco.soch.lab.service;

import java.util.ArrayList;
import java.util.List;
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

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.VLTestResultDto;
import gov.naco.soch.lab.mapper.VLTestResultMapper;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;

@Service
@Transactional
public class VLTestResultService {

	private static final Logger logger = LoggerFactory.getLogger(VLTestResultService.class);

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

	public List<VLTestResultDto> fetchVLTestResultsList(Long labId) {

		logger.debug("In fetchVLTestResultsList() of VLTestResultService");

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

		List<VLTestResultDto> vlTestResultDto = new ArrayList<>();
		List<LabTestSample> labTestSampleList = labTestSampleRepository.findByIsDelete(Boolean.FALSE);
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
			labTestSampleList = labTestSampleList.stream()
					.filter(isSampleInLab.and(sampleStatus).and(checkResultStatus)).collect(Collectors.toList());
			vlTestResultDto = labTestSampleList.stream().map(s -> VLTestResultMapper.mapToVLTestResultDto(s))
					.collect(Collectors.toList());
		}
		return vlTestResultDto;
	}

	public List<VLTestResultDto> fetchVLTestResultsUnderApproval(Long labId) {

		logger.debug("In fetchVLTestResultsUnderApproval() of VLTestResultService");

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

		List<VLTestResultDto> vlTestResultDto = new ArrayList<>();
		List<LabTestSample> labTestSampleList = labTestSampleRepository.findByIsDelete(Boolean.FALSE);
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
			labTestSampleList = labTestSampleList.stream()
					.filter(isSampleInLab.and(statusAccepted).and(checkResultStatus)).collect(Collectors.toList());
			vlTestResultDto = labTestSampleList.stream().map(s -> VLTestResultMapper.mapToVLTestResultDto(s))
					.collect(Collectors.toList());
		}
		return vlTestResultDto;
	}

	public List<VLTestResultDto> approveVLTestResults(Long labInchargeId, List<VLTestResultDto> vlTestResultList) {

		List<Long> idList = vlTestResultList.stream().map(s -> s.getSampleId()).collect(Collectors.toList());

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

		List<VLTestResultDto> vlTestResultDto = new ArrayList<>();
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			for (LabTestSample s : labTestSampleList) {
				s.setMasterSampleStatus(masterSampleStatus);
				s.setMasterResultStatus(masterResultStatus);
				s.setLabInCharge(labIncharge);
			}
			labTestSampleList = labTestSampleRepository.saveAll(labTestSampleList);

			vlTestResultDto = labTestSampleList.stream().map(s -> VLTestResultMapper.mapToVLTestResultDto(s))
					.collect(Collectors.toList());
		}

		// Change the status of batch
		changeBatchStatus(batchIds);
		return vlTestResultDto;
		// Handle the change of batch status
	}

	public List<VLTestResultDto> rejectVLTestResults(Long labInchargeId, List<VLTestResultDto> vlTestResultList) {

		List<Long> idList = vlTestResultList.stream().map(s -> s.getSampleId()).collect(Collectors.toList());

		List<LabTestSample> labTestSampleList = labTestSampleRepository.findAllById(idList);

		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("REJECTED",
				Boolean.FALSE);

		UserMaster labIncharge = null;
		Optional<UserMaster> labInchargeOpt = userMasterRepository.findById(labInchargeId);
		if (labInchargeOpt.isPresent()) {
			labIncharge = labInchargeOpt.get();
		} else {
			throw new ServiceException("Invalid User", null, HttpStatus.BAD_REQUEST);
		}

		List<VLTestResultDto> vlTestResultDto = new ArrayList<>();
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			for (LabTestSample s : labTestSampleList) {
				s.setMasterResultStatus(masterResultStatus);
				s.setLabInCharge(labIncharge);
				s.setArtcSampleStatus("RESULT POSTED");
			}
			labTestSampleList = labTestSampleRepository.saveAll(labTestSampleList);

			vlTestResultDto = labTestSampleList.stream().map(s -> VLTestResultMapper.mapToVLTestResultDto(s))
					.collect(Collectors.toList());
		}
		return vlTestResultDto;
		// Handle the change of batch status
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
}
