package gov.naco.soch.lab.mapper;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.lab.dto.VLTestResultDto;

public class VLTestResultMapper {

	public static VLTestResultDto mapToVLTestResultDto(LabTestSample labTestSample) {
		VLTestResultDto vlTestResultDto = new VLTestResultDto();

		vlTestResultDto.setBatchId(labTestSample.getLabTestSampleBatch().getId());
		vlTestResultDto.setSampleId(labTestSample.getId());
		vlTestResultDto.setBdnSerialNumber(labTestSample.getLabTestSampleBatch().getBdnSerialNumber());
		vlTestResultDto.setArtcId(labTestSample.getLabTestSampleBatch().getFacility().getId());
		vlTestResultDto.setArtcName(labTestSample.getLabTestSampleBatch().getFacility().getName());
		vlTestResultDto.setArtcAddress(labTestSample.getLabTestSampleBatch().getFacility().getAddress().getAddress());
		vlTestResultDto.setLabId(labTestSample.getLabTestSampleBatch().getLab().getId());
		vlTestResultDto.setLabName(labTestSample.getLabTestSampleBatch().getLab().getName());
		vlTestResultDto.setLabAddress(labTestSample.getLabTestSampleBatch().getLab().getAddress().getAddress());

		vlTestResultDto.setBeneficiaryId(labTestSample.getBeneficiary().getId());
		vlTestResultDto.setBeneficiaryUid(labTestSample.getBeneficiary().getUid());
		vlTestResultDto.setBeneficiaryName(labTestSample.getBeneficiary().getFirstName());
		vlTestResultDto.setBeneficiaryDob(labTestSample.getBeneficiary().getDateOfBirth());
		vlTestResultDto.setBeneficiaryAge(labTestSample.getBeneficiary().getAge());
		vlTestResultDto.setBeneficiaryGender(labTestSample.getBeneficiary().getGender());
		vlTestResultDto.setBarcodeNumber(labTestSample.getBarcodeNumber());

		vlTestResultDto.setSampleCollectedDate(labTestSample.getSampleCollectedDate());
		vlTestResultDto.setSampleReceivedDate(labTestSample.getSampleReceivedDate());
		vlTestResultDto.setResultReceivedDate(labTestSample.getResultReceivedDate());
		vlTestResultDto.setTypeOfSpecimen(labTestSample.getTypeOfSpecimen());
		vlTestResultDto.setResultValue(labTestSample.getResultValue());
		vlTestResultDto.setLogValue(labTestSample.getLogValue());
		vlTestResultDto.setIsError(labTestSample.getIsError());
		vlTestResultDto.setErrorCode(labTestSample.getErrorCode());

		if (labTestSample.getTestType() != null) {
			vlTestResultDto.setTestTypeId(labTestSample.getTestType().getId());
			vlTestResultDto.setTestType(labTestSample.getTestType().getTestType());
		}
		if (labTestSample.getMasterResultStatus() != null) {
			vlTestResultDto.setResultStatusId(labTestSample.getMasterResultStatus().getId());
			vlTestResultDto.setResultStatus(labTestSample.getMasterResultStatus().getStatus());
		}

		if (labTestSample.getResultType() != null) {
			vlTestResultDto.setResultTypeId(labTestSample.getResultType().getId());
			vlTestResultDto.setResultType(labTestSample.getResultType().getResultType());
		}

		return vlTestResultDto;
	}

}
