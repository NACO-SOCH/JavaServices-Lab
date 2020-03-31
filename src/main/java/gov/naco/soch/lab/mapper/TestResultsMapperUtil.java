package gov.naco.soch.lab.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.lab.dto.TestResultDto;

public class TestResultsMapperUtil {
	public static List<TestResultDto> mapTolabTestResultDto(List <LabTestSampleBatch> labTestSampleBatchList) {

		List<TestResultDto> labTestResultsDtoList=new ArrayList<> ();

		for(LabTestSampleBatch labTestSampleBatch:labTestSampleBatchList) {						
			Set<LabTestSample> labTestSampleSet=labTestSampleBatch.getLabTestSamples();			
			for(LabTestSample labTestSample:labTestSampleSet) {
				TestResultDto testResultDto=new TestResultDto();
				testResultDto.setArtcName(labTestSampleBatch.getFacility().getName());
				testResultDto.setAddress(labTestSampleBatch.getFacility().getAddress().getAddress());
				testResultDto.setArtNo((labTestSampleBatch.getFacility().getId()));
				testResultDto.setBdnSerialNo(labTestSampleBatch.getBdnSerialNumber());	
				testResultDto.setBeneficiaryAge(labTestSample.getBeneficiary().getAge());
				testResultDto.setBeneficiaryHivStatus(labTestSample.getBeneficiary().getStatus());
				testResultDto.setBeneficiaryName(labTestSample.getBeneficiary().getFirstName()+""+labTestSample.getBeneficiary().getMiddleName()+""+labTestSample.getBeneficiary().getLastName());
				testResultDto.setBeneficiaryUId(labTestSample.getBeneficiary().getUid());
				testResultDto.setBeneficiaryGender(labTestSample.getBeneficiary().getGender());				
				testResultDto.setBarCode(labTestSample.getBarcodeNumber());
				testResultDto.setSamplCollectedDate(labTestSample.getSampleCollectedDate());
				testResultDto.setSampleReceivedDate(labTestSample.getSampleReceivedDate());
				testResultDto.setSampleDispatchedDate(labTestSample.getSampleDispatchDate());
				testResultDto.setTestType(labTestSample.getTestType().getTestType());
				testResultDto.setTestTypeId(labTestSample.getTestType().getId());
				testResultDto.setBatchId(labTestSampleBatch.getId());
				testResultDto.setResultDate(labTestSample.getResultReceivedDate());
				
				if(null!=labTestSample.getResultType()) {
					testResultDto.setResultType(labTestSample.getResultType().getResultType());
				}
				if(null!=labTestSample.getResultValue()) {
					testResultDto.setResultValue(labTestSample.getResultValue());
				}
				if(null!=labTestSample.getLogValue()) {
					testResultDto.setLogvalue(labTestSample.getLogValue());
				}
				if(null!=labTestSample.getIsError()) {
					testResultDto.setIsError(labTestSample.getIsError());
				}
				if(null!=labTestSample.getErrorCode()) {
					testResultDto.setErrorCode(labTestSample.getErrorCode());
				}
				if(2==labTestSample.getMasterResultStatus().getId()) {
					testResultDto.setResultStatus(labTestSample.getMasterResultStatus().getStatus());
					testResultDto.setResultStatusId(labTestSample.getMasterResultStatus().getId());
					labTestResultsDtoList.add(testResultDto);
				}


			}
		}
		return labTestResultsDtoList;

	}


}
