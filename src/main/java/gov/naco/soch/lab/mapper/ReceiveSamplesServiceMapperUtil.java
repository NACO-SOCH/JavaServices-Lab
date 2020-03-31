package gov.naco.soch.lab.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.LabTestSampleDto;

public class ReceiveSamplesServiceMapperUtil {

	public static LabTestSampleBatchDto mapToLabTestSampleBatchDto(LabTestSampleBatch labTestSampleBatch) {

		LabTestSampleBatchDto labTestSampleBatchDto = new LabTestSampleBatchDto();
		labTestSampleBatchDto.setId(labTestSampleBatch.getId());
		labTestSampleBatchDto.setBdnSerialNumber(labTestSampleBatch.getBdnSerialNumber());
		labTestSampleBatchDto.setDispatchDate(labTestSampleBatch.getDispatchDate());
		labTestSampleBatchDto.setReceivedDate(labTestSampleBatch.getReceivedDate());
		labTestSampleBatchDto.setNum_ofSamples(labTestSampleBatch.getNumOfSamples());
		labTestSampleBatchDto.setAcceptedSamples(labTestSampleBatch.getAcceptedSamples());
		labTestSampleBatchDto.setRejectedSamples(labTestSampleBatch.getRejectedSamples());
		if (labTestSampleBatch.getMasterBatchStatus() != null) {
			labTestSampleBatchDto.setBatchStatusId(labTestSampleBatch.getMasterBatchStatus().getId());
			labTestSampleBatchDto.setBatchStatus(labTestSampleBatch.getMasterBatchStatus().getStatus());
		}

		if (labTestSampleBatch.getFacility() != null) {
			labTestSampleBatchDto.setArtcId(labTestSampleBatch.getFacility().getId());
			labTestSampleBatchDto.setArtcName(labTestSampleBatch.getFacility().getName());
			labTestSampleBatchDto.setArtcAddress(labTestSampleBatch.getFacility().getAddress().getAddress());
		}
		if (labTestSampleBatch.getLab() != null) {
			labTestSampleBatchDto.setLabId(labTestSampleBatch.getLab().getId());
			labTestSampleBatchDto.setLabName(labTestSampleBatch.getLab().getName());
			// to change address (null check address)
			labTestSampleBatchDto.setLabAddress(labTestSampleBatch.getLab().getAddress().getAddress());
		}
		if (labTestSampleBatch.getArtcLabTechUser() != null) {
			labTestSampleBatchDto.setArtcLabTechId(labTestSampleBatch.getArtcLabTechUser().getId());
			// change to full name
			labTestSampleBatchDto.setArtcLabTechName(labTestSampleBatch.getArtcLabTechUser().getFirstname());
			labTestSampleBatchDto.setArtcLabTechContact(labTestSampleBatch.getArtcLabTechUser().getMobileNumber());
		}
		if (labTestSampleBatch.getVlLabTechUser() != null) {
			labTestSampleBatchDto.setLabTechId(labTestSampleBatch.getVlLabTechUser().getId());
			// change to full name
			labTestSampleBatchDto.setLabTechName(labTestSampleBatch.getVlLabTechUser().getFirstname());
			labTestSampleBatchDto.setLabTechContact(labTestSampleBatch.getVlLabTechUser().getMobileNumber());
		}
		if (!CollectionUtils.isEmpty(labTestSampleBatch.getLabTestSamples())) {
			List<LabTestSampleDto> labTestSampleDtoList = new ArrayList<>();
			labTestSampleBatch.getLabTestSamples().forEach(s -> {
				labTestSampleDtoList.add(mapToLabTestSamplesDTO(s));
			});
			labTestSampleBatchDto.setLabTestSampleDtoList(labTestSampleDtoList);
		}
		return labTestSampleBatchDto;
	}

	private static LabTestSampleDto mapToLabTestSamplesDTO(LabTestSample s) {
		LabTestSampleDto labTestSampleDto = new LabTestSampleDto();
		labTestSampleDto.setId(s.getId());
		labTestSampleDto.setTestBatchId(s.getLabTestSampleBatch().getId());
		labTestSampleDto.setBeneficiaryId(s.getBeneficiary().getId());
		labTestSampleDto.setBeneficiaryName(s.getBeneficiary().getFirstName());
		labTestSampleDto.setBeneficiaryDob(s.getBeneficiary().getDateOfBirth());
		labTestSampleDto.setBeneficiaryAge(s.getBeneficiary().getAge());
		s.getBeneficiary().getArtBeneficiaryDetails().forEach(a -> {
			if (a.getIsDelete() == Boolean.FALSE) {
				labTestSampleDto.setArtId(a.getId());
				labTestSampleDto.setArtNo(a.getArtCentreCode());
			}
		});
		
		labTestSampleDto.setBarcodeNumber(s.getBarcodeNumber());
		if (s.getTestType() != null) {
			labTestSampleDto.setTestTypeId(s.getTestType().getId());
			labTestSampleDto.setTestType(s.getTestType().getTestType());
		}
		if (s.getMasterSampleStatus() != null) {
			labTestSampleDto.setSampleStatusId(s.getMasterSampleStatus().getId());
			labTestSampleDto.setSampleStatus(s.getMasterSampleStatus().getStatus());
		}
		if (s.getMasterRemark() != null) {
			labTestSampleDto.setRemarksId(s.getMasterRemark().getId());
			labTestSampleDto.setRemarks(s.getMasterRemark().getRemarks());
		}
		if (s.getMasterResultStatus() != null) {
			labTestSampleDto.setResultStatusId(s.getMasterResultStatus().getId());
			labTestSampleDto.setResultStatus(s.getMasterResultStatus().getStatus());
		}
		labTestSampleDto.setSampleCollectedDate(s.getSampleCollectedDate());
		labTestSampleDto.setSampleDispatchDate(s.getSampleDispatchDate());
		labTestSampleDto.setSampleReceivedDate(s.getSampleReceivedDate());
		labTestSampleDto.setResultReceivedDate(s.getResultReceivedDate());
		labTestSampleDto.setResultApprovedDate(s.getResultApprovedDate());
		labTestSampleDto.setResultDispatchDate(s.getResultDispatchDate());
		if (s.getResultType() != null) {
			labTestSampleDto.setResultTypeId(s.getResultType().getId());
			labTestSampleDto.setResultType(s.getResultType().getResultType());
		}
		labTestSampleDto.setResultValue(s.getResultValue());
		labTestSampleDto.setLogValue(s.getLogValue());
		labTestSampleDto.setIsError(s.getIsError());
		labTestSampleDto.setErrorCode(s.getErrorCode());
//		labTestSampleDto.setTestMachineTypeId();
		if (s.getMachine() != null) {
			labTestSampleDto.setTestMachineId(s.getMachine().getId());
			labTestSampleDto.setTestMachine(s.getMachine().getMachineName());
		}
		if (s.getAuthorizer() != null) {
			labTestSampleDto.setAuthorizerId(s.getAuthorizer().getId());
		}
		labTestSampleDto.setAuthorizerSignature(s.getAuthorizerSignature());
		labTestSampleDto.setTypeOfSpecimen(s.getTypeOfSpecimen());
		if (s.getLabTecnician() != null) {
			labTestSampleDto.setLabTechnicianId(s.getLabTecnician().getId());
			labTestSampleDto.setLabTechnicianName(s.getLabTecnician().getFirstname());
			labTestSampleDto.setLabTechnicianSignature(s.getLabTechnicianSignature());
		}
		if (s.getLabInCharge() != null) {
			labTestSampleDto.setLabInchargeId(s.getLabInCharge().getId());
			labTestSampleDto.setLabInchargeName(s.getLabInCharge().getFirstname());
			labTestSampleDto.setLabInchargeSignature(s.getLabInchargeSignature());
		}
		labTestSampleDto.setTestRequestFormLink(s.getTestRequestFormLink());
		return labTestSampleDto;
	}

}
