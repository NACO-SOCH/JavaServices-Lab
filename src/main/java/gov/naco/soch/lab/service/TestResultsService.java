package gov.naco.soch.lab.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.exception.ServiceException;
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

	public boolean approveLabSampleResult(Long sampleId) {

		boolean  isApproved=false;
		Optional<LabTestSample>  labTestSample=labTestSampleRepository.findById(sampleId);
		
		if(labTestSample.isPresent())
		{ 

			MasterResultStatus masterResultStatus=new 	 MasterResultStatus(); 
			//setting status to 3 which is Approved status
			masterResultStatus.setId(new Long(3));
			labTestSample.get().setMasterResultStatus(masterResultStatus);
			isApproved=true; 
			}
		    else { 
		    	throw new ServiceException(" SampleID not present",null, HttpStatus.BAD_REQUEST);

			}

		return isApproved; 
	}

}
