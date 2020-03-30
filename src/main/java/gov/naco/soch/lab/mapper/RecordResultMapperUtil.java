package gov.naco.soch.lab.mapper;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.MasterResultType;
import gov.naco.soch.lab.dto.LabTestSampleDto;

public class RecordResultMapperUtil {
	public static LabTestSample mapToLabTestSample(LabTestSample labTestSample,LabTestSampleDto labTestSampleDto) {


		if(null!=labTestSampleDto.getErrorCode()) {                                                                                                                                                                                                                                                               
			labTestSample.setErrorCode(labTestSampleDto.getErrorCode());
		}
		if(null!=labTestSampleDto.getIsError()) {
			labTestSample.setIsError(labTestSampleDto.getIsError());
		}

		if(null!=labTestSampleDto.getResultReceivedDate()) {
			labTestSample.setResultReceivedDate(labTestSampleDto.getResultReceivedDate());
		}

		if(null!=labTestSampleDto.getResultTypeId()) {
			
			MasterResultType resultType=new MasterResultType();
			resultType.setId(labTestSampleDto.getResultTypeId());
			labTestSample.setResultType(resultType);
		}

		if(null!=labTestSampleDto.getResultValue()) {
			labTestSample.setResultValue(labTestSampleDto.getResultValue());
		}

		if(null!=labTestSampleDto.getLogValue()) {
			labTestSample.setLogValue(labTestSampleDto.getLogValue());
		}

		if(null!=labTestSampleDto.getSampleReceviedDate()) {
			labTestSample.setSampleReceivedDate(labTestSampleDto.getSampleReceviedDate());
		}
		return labTestSample;
	}

}
