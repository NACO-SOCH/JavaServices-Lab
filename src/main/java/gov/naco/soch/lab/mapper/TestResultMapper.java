package gov.naco.soch.lab.mapper;

import java.time.format.DateTimeFormatter;

import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.util.LabServiceUtil;

public class TestResultMapper {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static TestResultDto mapToTestResultDto(LabTestSample labTestSample) {
		TestResultDto vlTestResultDto = new TestResultDto();

		vlTestResultDto.setBatchId(labTestSample.getLabTestSampleBatch().getId());
		vlTestResultDto.setSampleId(labTestSample.getId());
		vlTestResultDto.setBdnSerialNumber(labTestSample.getLabTestSampleBatch().getBdnSerialNumber());
		vlTestResultDto.setArtcId(labTestSample.getLabTestSampleBatch().getFacility().getId());
		vlTestResultDto.setArtcName(labTestSample.getLabTestSampleBatch().getFacility().getName());
		vlTestResultDto.setArtcAddress(labTestSample.getLabTestSampleBatch().getFacility().getAddress().getAddress());
		vlTestResultDto.setArtcCode(labTestSample.getLabTestSampleBatch().getFacility().getCode());
		vlTestResultDto.setArtcArtCode(labTestSample.getLabTestSampleBatch().getFacility().getArtcode());
		vlTestResultDto.setLabId(labTestSample.getLabTestSampleBatch().getLab().getId());
		vlTestResultDto.setLabName(labTestSample.getLabTestSampleBatch().getLab().getName());
		vlTestResultDto.setLabAddress(labTestSample.getLabTestSampleBatch().getLab().getAddress().getAddress());
		vlTestResultDto.setLabCode(labTestSample.getLabTestSampleBatch().getLab().getCode());

		vlTestResultDto.setDispatchDate(labTestSample.getLabTestSampleBatch().getDispatchDate());
		vlTestResultDto.setReceivedDate(labTestSample.getLabTestSampleBatch().getReceivedDate());
		vlTestResultDto.setNum_ofSamples(labTestSample.getLabTestSampleBatch().getNumOfSamples());
		vlTestResultDto.setAcceptedSamples(labTestSample.getLabTestSampleBatch().getAcceptedSamples());

		if (labTestSample.getLabTestSampleBatch().getArtcLabTechUser() != null) {
			vlTestResultDto.setArtcLabTechId(labTestSample.getLabTestSampleBatch().getArtcLabTechUser().getId());
			// change to full name
			vlTestResultDto.setArtcLabTechName(
					LabServiceUtil.getUserName(labTestSample.getLabTestSampleBatch().getArtcLabTechUser()));
			vlTestResultDto.setArtcLabTechContact(
					labTestSample.getLabTestSampleBatch().getArtcLabTechUser().getMobileNumber());
		}

		vlTestResultDto.setBeneficiaryId(labTestSample.getBeneficiary().getId());
		vlTestResultDto.setBeneficiaryUid(labTestSample.getBeneficiary().getUid());
		vlTestResultDto.setBeneficiaryName(LabServiceUtil.getBeneficiaryName(labTestSample.getBeneficiary()));
		vlTestResultDto.setBeneficiaryDob(labTestSample.getBeneficiary().getDateOfBirth());
		vlTestResultDto.setBeneficiaryAge(labTestSample.getBeneficiary().getAge());
		vlTestResultDto.setBeneficiaryGender(labTestSample.getBeneficiary().getGender());
		vlTestResultDto.setBarcodeNumber(labTestSample.getBarcodeNumber());

		/*if (!CollectionUtils.isEmpty(labTestSample.getBeneficiary().getArtBeneficiaryDetails())) {

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
		}*/

		if (labTestSample.getSampleDispatchDate() != null) {
			vlTestResultDto.setSampleDispatchDate(labTestSample.getSampleDispatchDate().format(formatter));
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
			vlTestResultDto.setLabInChargeName(LabServiceUtil.getUserName(labTestSample.getLabInCharge()));
			vlTestResultDto.setLabInChargeContact(labTestSample.getLabInCharge().getMobileNumber());
		}

		if (labTestSample.getLabTecnician() != null) {
			vlTestResultDto.setLabTechnicianId(labTestSample.getLabTecnician().getId());
			vlTestResultDto.setLabTechnicianName(LabServiceUtil.getUserName(labTestSample.getLabTecnician()));
			vlTestResultDto.setLabTechnicianContact(labTestSample.getLabTecnician().getMobileNumber());
		}

		if (labTestSample.getAuthorizer() != null) {
			vlTestResultDto.setAuthorizerId(labTestSample.getAuthorizer().getId());
			vlTestResultDto.setAuthorizerName(LabServiceUtil.getUserName(labTestSample.getAuthorizer()));
			vlTestResultDto.setAuthorizerSignature(labTestSample.getAuthorizerSignature());
		}

		if (labTestSample.getMachine() != null) {
			vlTestResultDto.setTestMachineId(labTestSample.getMachine().getId());
			vlTestResultDto.setTestMachineName(labTestSample.getMachine().getMachineName());
		}

		return vlTestResultDto;
	}

}
