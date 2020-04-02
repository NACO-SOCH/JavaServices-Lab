package gov.naco.soch.lab.mapper;

import java.time.format.DateTimeFormatter;

import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.lab.dto.VLTestResultDto;

public class VLTestResultMapper {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

		if (!CollectionUtils.isEmpty(labTestSample.getBeneficiary().getArtBeneficiaryDetails())) {

			labTestSample.getBeneficiary().getArtBeneficiaryDetails().forEach(d -> {
				if (d.getIsDelete() == Boolean.FALSE) {
					vlTestResultDto.setArtNumber(d.getArtNumber());
					vlTestResultDto.setPreArtNumber(d.getPreArtNumber());
					if (!CollectionUtils.isEmpty(d.getArtPatientAssessments())) {
						d.getArtPatientAssessments().forEach(pa -> {
							if (pa.getIsDelete() != Boolean.FALSE) {
								vlTestResultDto.setBeneficiaryHivStatus(pa.getHivStatus());
							}
						});
					}
				}
			});
		}
		if (labTestSample.getSampleCollectedDate() != null) {
			vlTestResultDto.setSampleCollectedDate(labTestSample.getSampleCollectedDate().format(formatter));
		}

		if (labTestSample.getSampleReceivedDate() != null) {
			vlTestResultDto.setSampleReceivedDate(labTestSample.getSampleReceivedDate().format(formatter));
		}

		if (labTestSample.getMasterSampleStatus() != null) {
			vlTestResultDto.setSampleStatusId(labTestSample.getMasterSampleStatus().getId());
			vlTestResultDto.setSampleStatus(labTestSample.getMasterSampleStatus().getStatus());
		}
		if (labTestSample.getResultReceivedDate() != null) {
			vlTestResultDto.setResultReceivedDate(labTestSample.getResultReceivedDate().format(formatter));
		}

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

		if (labTestSample.getResultDispatchDate() != null) {
			vlTestResultDto.setResultDispatchDate(labTestSample.getResultDispatchDate().format(formatter));
		}

		if (labTestSample.getLabInCharge() != null) {
			vlTestResultDto.setLabInChargeId(labTestSample.getLabInCharge().getId());
			vlTestResultDto.setLabInChargeName(labTestSample.getLabInCharge().getFirstname());
		}

		if (labTestSample.getLabTecnician() != null) {
			vlTestResultDto.setLabTechnicianId(labTestSample.getLabTecnician().getId());
			vlTestResultDto.setLabTechnicianName(labTestSample.getLabTecnician().getFirstname());
		}

		if (labTestSample.getMachine() != null) {
			vlTestResultDto.setTestMachineId(labTestSample.getMachine().getId());
			vlTestResultDto.setTestMachineName(labTestSample.getMachine().getMachineName());
		}

		return vlTestResultDto;
	}

}
