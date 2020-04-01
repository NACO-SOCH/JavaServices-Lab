package gov.naco.soch.lab.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterRemark;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.entity.UserMaster;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.mapper.ReceiveSamplesServiceMapperUtil;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterRemarkRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;
import gov.naco.soch.repository.UserMasterRepository;

@Service
@Transactional
public class ReceiveSamplesService {

	@Autowired
	private LabTestSampleBatchRepository labTestSampleBatchRepository;

	@Autowired
	private UserMasterRepository userMasterRepository;

	@Autowired
	private MasterSampleStatusRepository masterSampleStatusRepository;

	@Autowired
	private MasterBatchStatusRepository masterBatchStatusRepository;

	@Autowired
	private MasterRemarkRepository masterRemarkRepository;

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
		}
		return labTestSampleBatchDtoList;
	}

	public LabTestSampleBatchDto saveReceivedSamples(Long batchId, LabTestSampleBatchDto labTestSampleBatchDto) {

		Optional<LabTestSampleBatch> labTestSampleBatchOpt = labTestSampleBatchRepository
				.findById(batchId);
		if (labTestSampleBatchOpt.isPresent()) {
			LabTestSampleBatch labTestSampleBatch = labTestSampleBatchOpt.get();
			labTestSampleBatch.setAcceptedSamples(labTestSampleBatchDto.getAcceptedSamples());
			labTestSampleBatch.setRejectedSamples(labTestSampleBatchDto.getRejectedSamples());
			labTestSampleBatch.setReceivedDate(LocalDateTime.now());
			Optional<UserMaster> labTechUserOpt = userMasterRepository.findById(labTestSampleBatchDto.getLabTechId());
			if (labTechUserOpt.isPresent()) {
				labTestSampleBatch.setVlLabTechUser(labTechUserOpt.get());
			} else {
				// throw error
			}
			Optional<MasterBatchStatus> batchStatusOpt = masterBatchStatusRepository
					.findById(labTestSampleBatchDto.getBatchStatusId());
			if (batchStatusOpt.isPresent()) {
				labTestSampleBatch.setMasterBatchStatus(batchStatusOpt.get());
			}
			if (!CollectionUtils.isEmpty(labTestSampleBatch.getLabTestSamples())
					&& !CollectionUtils.isEmpty(labTestSampleBatchDto.getLabTestSampleDtoList())) {

				labTestSampleBatch.getLabTestSamples().forEach(s -> {

					Optional<LabTestSampleDto> sampleOpt = labTestSampleBatchDto.getLabTestSampleDtoList().stream()
							.filter(ts -> ts.getId() == s.getId()).findFirst();
					if (sampleOpt.isPresent()) {
						LabTestSampleDto sample = sampleOpt.get();

						if (sample.getSampleStatusId() != null && sample.getSampleStatusId() != 0L) {
							Optional<MasterSampleStatus> sampleStatusOpt = masterSampleStatusRepository
									.findById(sample.getSampleStatusId());
							if (sampleStatusOpt.isPresent()) {
								s.setMasterSampleStatus(sampleStatusOpt.get());
							} else {
								// throw error
							}
						}

						if (sample.getRemarksId() != null && sample.getRemarksId() != 0L) {
							Optional<MasterRemark> remarkOpt = masterRemarkRepository.findById(sample.getRemarksId());
							if (remarkOpt.isPresent()) {
								s.setMasterRemark(remarkOpt.get());
							} else {
								// throw error
							}
						}
					}
				});
			}
			LabTestSampleBatch labTestSampleBatchSaved = labTestSampleBatchRepository.save(labTestSampleBatch);
			return ReceiveSamplesServiceMapperUtil.mapToLabTestSampleBatchDto(labTestSampleBatchSaved);
		} else {
			// throw error
		}
		return labTestSampleBatchDto;
	}

}
