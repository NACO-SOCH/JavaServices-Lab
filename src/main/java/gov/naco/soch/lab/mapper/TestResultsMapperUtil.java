package gov.naco.soch.lab.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.dto.TestResultDto;

public class TestResultsMapperUtil {
	public static List<TestResultDto> mapTolabTestResultDto(List <LabTestSampleBatch> labTestSampleBatchList) {

		List<TestResultDto> labTestResultsDtoList=new ArrayList<> ();

		for(LabTestSampleBatch labTestSampleBatch:labTestSampleBatchList) {

			TestResultDto testResultDto=new TestResultDto();
			testResultDto.setArtCName(labTestSampleBatch.getFacility().getName());
			testResultDto.setArtNo((labTestSampleBatch.getFacility().getId()));
			testResultDto.setBdnSerialNo(labTestSampleBatch.getBdnSerialNumber());				
			Set<LabTestSample> labTestSampleSet=labTestSampleBatch.getLabTestSamples();			
			for(LabTestSample labTestSample:labTestSampleSet) {
				testResultDto.setBeneficiaryAge(labTestSample.getBeneficiary().getAge());
				testResultDto.setBeneficiaryHivStatus(labTestSample.getBeneficiary().getStatus());
				testResultDto.setBeneficiaryName(labTestSample.getBeneficiary().getFirstName()+""+labTestSample.getBeneficiary().getMiddleName()+""+labTestSample.getBeneficiary().getLastName());
				testResultDto.setBeneficiaryUId(labTestSample.getBeneficiary().getUid());
				testResultDto.setBeneficiaryGender(labTestSample.getBeneficiary().getGender());				
				testResultDto.setTestName(labTestSample.getTestType().getTypeOfTest());//doubt
				testResultDto.setBarCode(labTestSample.getBarcodeNumber());
				testResultDto.setSamplCollectedDate(labTestSample.getSampleCollectedDate());
				testResultDto.setSampleReceivedDate(labTestSample.getSampleReceivedDate());
				testResultDto.setSampleDispatchedDate(labTestSample.getSampleDispatchDate());
				labTestResultsDtoList.add(testResultDto);
			}
		}
		return labTestResultsDtoList;

	}

	private static List<LabTestSampleDto> mapTolabTestSampleDtoList(Set<LabTestSample> labTestSampleList) {

		List<LabTestSampleDto> labTestSampleDtoSet=new ArrayList<>();

		for(LabTestSample labTestSample:labTestSampleList) {
			LabTestSampleDto labTestSampleDto=new LabTestSampleDto();
			labTestSampleDto.setBarcodeNumber(labTestSample.getBarcodeNumber());
			labTestSampleDto.setSampleCollectedDate(labTestSample.getSampleCollectedDate());
			labTestSampleDto.setSampleReceivedDate(labTestSample.getResultReceivedDate());			
			//labTestSampleDto.setb(labTestSample.getBeneficiary().getAge());
			//	labTestSampleDto.setbe(labTestSample.getBeneficiary().getStatus());//doubt
			labTestSampleDto.setBeneficiaryId(labTestSample.getBeneficiary().getId());
			labTestSampleDto.setBeneficiaryName(labTestSample.getBeneficiary().getFirstName()+" "+labTestSample.getBeneficiary().getMiddleName()+" "+labTestSample.getBeneficiary().getLastName());
			labTestSampleDto.setLabInchargeName(labTestSample.getLabInCharge().getFirstname()+""+labTestSample.getLabInCharge().getLastname());
			labTestSampleDto.setLabTechnicianName(labTestSample.getLabTecnician().getFirstname()+""+labTestSample.getLabTecnician().getLastname());
			labTestSampleDto.setTestMachineId(labTestSample.getTestMachineTypeId());
			//labTestSampleDto.se(labTestSample.getMachine().getMachineName());			
			labTestSampleDtoSet.add(labTestSampleDto);
		}
		return labTestSampleDtoSet;
	}
}
