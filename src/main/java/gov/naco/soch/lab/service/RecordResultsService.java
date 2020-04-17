package gov.naco.soch.lab.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterResultType;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.VLTestResultDto;
import gov.naco.soch.lab.mapper.VLTestResultMapper;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterResultTypeRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;

@Service
@Transactional
public class RecordResultsService {

	private static final Logger logger = LoggerFactory.getLogger(VLTestResultService.class);

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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

//	@Autowired
//	private LabTestSampleBatchRepository labTestSampleBatchRepository;

	public List<VLTestResultDto> getRecordResultsList(Long labId) {

		logger.debug("In getRecordResultsList() of RecordResultsService");

		MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
				Boolean.FALSE);

		MasterResultStatus masterResultStatus = masterResultStatusRepository.findByStatusAndIsDelete("PENDING",
				Boolean.FALSE);

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

	public VLTestResultDto saveRecordResult(Long sampleId, VLTestResultDto labTestSampleDto) {

		logger.debug("In getRecordResultsList() of RecordResultsService");

		Optional<LabTestSample> labTestSampleOpt = labTestSampleRepository.findById(sampleId);

		MasterResultStatus masterResultStatus = masterResultStatusRepository
				.findByStatusAndIsDelete("AWAITING APPROVAL", Boolean.FALSE);

		MasterResultStatus masterResultStatusError = masterResultStatusRepository.findByStatusAndIsDelete("ERROR",
				Boolean.FALSE);

		if (labTestSampleOpt.isPresent()) {

			LabTestSample labTestSample = labTestSampleOpt.get();
			if (labTestSampleDto.getResultReceivedDate() != null) {
				labTestSample.setResultReceivedDate(
						LocalDateTime.parse(labTestSampleDto.getResultReceivedDate(), formatter));
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

			if (labTestSampleDto.getIsError()!=null && labTestSampleDto.getIsError()) {
				labTestSample.setMasterResultStatus(masterResultStatusError);
			}

			if (labTestSampleDto.getResultTypeId() != null) {
				Optional<MasterResultType> resultTypeOpt = masterResultTypeRepository
						.findById(labTestSampleDto.getResultTypeId());
				if (resultTypeOpt.isPresent()) {
					labTestSample.setResultType(resultTypeOpt.get());
				}
			}

			labTestSample = labTestSampleRepository.save(labTestSample);
			return VLTestResultMapper.mapToVLTestResultDto(labTestSample);
		} else {
			throw new ServiceException("SampleID not present", null, HttpStatus.BAD_REQUEST);
		}
	}

	public List<VLTestResultDto> getRecordResultsArtcList(Long artcId) {

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
}
