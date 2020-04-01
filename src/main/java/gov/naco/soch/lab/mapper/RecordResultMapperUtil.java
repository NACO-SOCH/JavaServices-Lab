package gov.naco.soch.lab.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterResultType;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.dto.VLTestResultDto;


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

		if(null!=labTestSampleDto.getSampleReceivedDate()) {
			labTestSample.setSampleReceivedDate(labTestSampleDto.getSampleReceivedDate());
		}
		return labTestSample;
	}
	///

	public static List<VLTestResultDto> mapTolabVLTestResultDto(List <LabTestSampleBatch> labTestSampleBatchList) {

		List<VLTestResultDto> labTestResultsDtoList=new ArrayList<> ();

		for(LabTestSampleBatch labTestSampleBatch:labTestSampleBatchList) {
			Set<LabTestSample> labTestSampleSet=labTestSampleBatch.getLabTestSamples();    
			
			for(LabTestSample labTestSample:labTestSampleSet) {
				VLTestResultDto testResultDto=new VLTestResultDto();
				testResultDto.setArtcName(labTestSampleBatch.getFacility().getName());
				testResultDto.setArtcId(labTestSampleBatch.getFacility().getId());
				testResultDto.setBdnSerialNumber(labTestSampleBatch.getBdnSerialNumber()); 
				testResultDto.setBeneficiaryAge(labTestSample.getBeneficiary().getAge());
				testResultDto.setBeneficiaryHivStatus(labTestSample.getBeneficiary().getStatus());
				testResultDto.setBeneficiaryName(labTestSample.getBeneficiary().getFirstName()+""+labTestSample.getBeneficiary().getMiddleName()+""+labTestSample.getBeneficiary().getLastName());
				testResultDto.setBeneficiaryUid(labTestSample.getBeneficiary().getUid());
				testResultDto.setBeneficiaryGender(labTestSample.getBeneficiary().getGender());                                                          
				testResultDto.setBarcodeNumber(labTestSample.getBarcodeNumber());
				testResultDto.setSampleCollectedDate(labTestSample.getSampleCollectedDate());
				testResultDto.setSampleReceivedDate(labTestSample.getSampleReceivedDate());
				testResultDto.setTestType(labTestSample.getTestType().getTestType());
				//check if the sample status is accepted
				if(1==labTestSample.getMasterSampleStatus().getId()) {
					labTestResultsDtoList.add(testResultDto);
				}
				
			}
		}
		return labTestResultsDtoList;

	} 


}
