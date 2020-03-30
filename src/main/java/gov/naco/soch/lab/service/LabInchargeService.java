package gov.naco.soch.lab.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.mapper.LabInChargeMapper;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;

@Service
@Transactional
public class LabInchargeService {
	@Autowired
	private LabTestSampleRepository labTestSampleRepository;
	
	@Autowired
	private LabTestSampleBatchRepository labTestSampleBatchRepository;
	private static final Logger logger = LoggerFactory.getLogger(LabInchargeService.class);
	
	
	/*  public List<LabTestSampleBatchDto> getTestResultDetails(Long labId){
	  List<LabTestSampleBatch> labTestBatchList=labTestSampleBatchRepository.findAllByLabId(labId);
	  List<LabTestSampleBatchDto> labTestSampleBatchDtoList=LabInChargeMapper.mapTolabTestSampleBatchDto(labTestBatchList);
	  return labTestSampleBatchDtoList;*/
	  }
	 

}
