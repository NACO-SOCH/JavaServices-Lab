package gov.naco.soch.lab.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.dto.VLTestResultDto;
import gov.naco.soch.lab.mapper.RecordResultMapperUtil;
import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
@Service
@Transactional
public class RecordResultService {


	@Autowired
	private LabTestSampleRepository labTestSampleRepository;
//
	

	@Autowired
	private LabTestSampleBatchRepository labTestSampleBatchRepository;

	public boolean saveLabSampleResult(LabTestSampleDto labTestSampleDto) {

		boolean isSaved=false;		
		LabTestSample labTestSample=null;
		if(null==labTestSampleDto.getId()) {			
			labTestSample=new LabTestSample();
			MasterResultStatus masterResultStatus=new MasterResultStatus();
			masterResultStatus.setId(new Long(2));
			labTestSample.setMasterResultStatus(masterResultStatus);
		}
		else {

			Optional<LabTestSample> labTestSampleObj=labTestSampleRepository.findById(labTestSampleDto.getId());
			if(labTestSampleObj.isPresent()) {
				labTestSample=labTestSampleObj.get();
			}
			else
			{			
				throw new ServiceException("SampleID not present", null, HttpStatus.BAD_REQUEST);

			}
		}

		labTestSample=RecordResultMapperUtil.mapToLabTestSample(labTestSample,labTestSampleDto);
		labTestSampleRepository.save(labTestSample);
		isSaved=true;	
		return isSaved;
	}
//
	

	  public List<VLTestResultDto> getRecordResultDetails(Long labId){
	  List<LabTestSampleBatch> labTestBatchList= labTestSampleBatchRepository.findAllByLabId(labId);
	  List<VLTestResultDto> vlTestResultDtoList=RecordResultMapperUtil.mapTolabVLTestResultDto(labTestBatchList); 
	  return vlTestResultDtoList;
	  
	  }
	  
}
