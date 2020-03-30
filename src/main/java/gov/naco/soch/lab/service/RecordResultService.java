package gov.naco.soch.lab.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.mapper.RecordResultMapperUtil;
import gov.naco.soch.repository.LabTestSampleRepository;

public class RecordResultService {
	
	
	@Autowired
	private LabTestSampleRepository labTestSampleRepository;
	
		
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

}
