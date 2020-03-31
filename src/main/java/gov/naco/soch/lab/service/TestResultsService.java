package gov.naco.soch.lab.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.mapper.TestResultsMapperUtil;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;

@Service
@Transactional
public class TestResultsService {
	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private LabTestSampleBatchRepository labTestSampleBatchRepository;
	private static final Logger logger = LoggerFactory.getLogger(LabInchargeService.class);


	public List<TestResultDto> getTestResultDetails(Long labId){
		List<LabTestSampleBatch> labTestBatchList=labTestSampleBatchRepository.findAllByLabId(labId);
		List<TestResultDto> labTestResultDtoList=TestResultsMapperUtil.mapTolabTestResultDto(labTestBatchList);
		return labTestResultDtoList;
	}


}
