package gov.naco.soch.lab.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.lab.dto.VLTestResultDto;
import gov.naco.soch.lab.mapper.VLTestResultMapper;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterResultStatusRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;

@Service
@Transactional
public class VLTestResultService {

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private MasterSampleStatusRepository masterSampleStatusRepository;

	@Autowired
	private MasterResultStatusRepository masterResultStatusRepository;

	@Autowired
	private MasterBatchStatusRepository masterBatchStatusRepository;

	public List<VLTestResultDto> fetchVLTestResultsList(Long labId) {

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

		Predicate<LabTestSample> checkResultStatus = s -> s.getMasterResultStatus().getId() != masterResultStatus
				.getId();

		List<VLTestResultDto> vlTestResultDto = new ArrayList<>();
		List<LabTestSample> labTestSampleList = labTestSampleRepository.findByIsDelete(Boolean.FALSE);
		if (!CollectionUtils.isEmpty(labTestSampleList)) {
			labTestSampleList = labTestSampleList.stream()
					.filter(checkBatchStatus).collect(Collectors.toList());
			labTestSampleList = labTestSampleList.stream()
					.filter(isSampleInLab.and(statusAccepted).and(checkResultStatus))
					.collect(Collectors.toList());
			vlTestResultDto = labTestSampleList.stream().map(s -> VLTestResultMapper.mapToVLTestResultDto(s))
					.collect(Collectors.toList());
		}
		return vlTestResultDto;
	}
}
