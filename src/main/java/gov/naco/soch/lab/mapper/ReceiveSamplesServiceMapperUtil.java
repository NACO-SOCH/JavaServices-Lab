package gov.naco.soch.lab.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.Address;
import gov.naco.soch.entity.ArtBeneficiary;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.util.LabServiceUtil;

public class ReceiveSamplesServiceMapperUtil {

	public static LabTestSampleBatchDto mapToLabTestSampleBatchDto(LabTestSampleBatch labTestSampleBatch) {

		LabTestSampleBatchDto labTestSampleBatchDto = new LabTestSampleBatchDto();
		labTestSampleBatchDto.setBatchId(labTestSampleBatch.getId());
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

			Address labAddress = labTestSampleBatch.getFacility().getAddress();
			String labAddressString = (labAddress.getAddressLineOne() != null ? labAddress.getAddressLineOne() : "")
					+ (labAddress.getAddressLineTwo() != null ? ", " + labAddress.getAddressLineTwo() : "");

			labTestSampleBatchDto.setArtcAddress(labAddressString);
			labTestSampleBatchDto.setArtcCode(labTestSampleBatch.getFacility().getCode());
			labTestSampleBatchDto.setArtcArtCode(labTestSampleBatch.getFacility().getArtcode());
		}
		if (labTestSampleBatch.getLab() != null) {
			labTestSampleBatchDto.setLabId(labTestSampleBatch.getLab().getId());
			labTestSampleBatchDto.setLabName(labTestSampleBatch.getLab().getName());
			// to change address (null check address)

			Address labAddress = labTestSampleBatch.getLab().getAddress();
			String labAddressString = (labAddress.getAddressLineOne() != null ? labAddress.getAddressLineOne() : "")
					+ (labAddress.getAddressLineTwo() != null ? ", " + labAddress.getAddressLineTwo() : "");
			
			if (!labAddressString.equals(", ")) {
				labTestSampleBatchDto.setLabAddress(labAddressString);
			}
			labTestSampleBatchDto.setLabCode(labTestSampleBatch.getLab().getCode());
		}
		if (labTestSampleBatch.getArtcLabTechUser() != null) {
			labTestSampleBatchDto.setArtcLabTechId(labTestSampleBatch.getArtcLabTechUser().getId());
			// change to full name
			labTestSampleBatchDto
					.setArtcLabTechName(LabServiceUtil.getUserName(labTestSampleBatch.getArtcLabTechUser()));
			labTestSampleBatchDto.setArtcLabTechContact(labTestSampleBatch.getArtcLabTechUser().getMobileNumber());
		}
		if (labTestSampleBatch.getVlLabTechUser() != null) {
			labTestSampleBatchDto.setLabTechnicianId(labTestSampleBatch.getVlLabTechUser().getId());
			// change to full name
			labTestSampleBatchDto
					.setLabTechnicianName(LabServiceUtil.getUserName(labTestSampleBatch.getVlLabTechUser()));
			labTestSampleBatchDto.setLabTechnicianContact(labTestSampleBatch.getVlLabTechUser().getMobileNumber());
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
		labTestSampleDto.setSampleId(s.getId());
		labTestSampleDto.setBatchId(s.getLabTestSampleBatch().getId());
		labTestSampleDto.setBeneficiaryId(s.getBeneficiary().getId());
		labTestSampleDto.setBeneficiaryName(LabServiceUtil.getBeneficiaryName(s.getBeneficiary()));
		labTestSampleDto.setBeneficiaryUid(s.getBeneficiary().getUid());
		labTestSampleDto.setBeneficiaryDob(s.getBeneficiary().getDateOfBirth());
		labTestSampleDto.setBeneficiaryAge(s.getBeneficiary().getAge());
		labTestSampleDto.setArtNo(s.getBeneficiary().getArtNumber());
		labTestSampleDto.setPreArtNumber(s.getBeneficiary().getPreArtNumber());
		labTestSampleDto.setIctcDnaCode(s.getLabTestSampleBatch().getFacility().getCode());

		if (s.getBeneficiary().getGenderId() != null) {
			labTestSampleDto.setBeneficiaryGender(s.getBeneficiary().getGenderId().getName());
		} else {
			labTestSampleDto.setBeneficiaryGender(s.getBeneficiary().getGender());
		}

		if (!CollectionUtils.isEmpty(s.getBeneficiary().getArtBeneficiary())) {
			if (s.getBeneficiary().getArtBeneficiary().iterator().hasNext()) {

				ArtBeneficiary artDetails = s.getBeneficiary().getArtBeneficiary().iterator().next();
				if(artDetails.getMasterArtBeneficiaryStatus()!=null) {
					labTestSampleDto.setBeneficiaryHivStatus(artDetails.getMasterArtBeneficiaryStatus().getName());
				}
				if (artDetails.getMasterRiskFactor() != null) {
					labTestSampleDto.setPopulationType(artDetails.getMasterRiskFactor().getName());
				}
			}
		}

		/*
		 * if (!CollectionUtils.isEmpty(s.getBeneficiary().getArtBeneficiaryDetails()))
		 * {
		 * 
		 * s.getBeneficiary().getArtBeneficiaryDetails().forEach(d -> { if
		 * (d.getIsDelete() == Boolean.FALSE) {
		 * labTestSampleDto.setArtNumber(d.getArtNumber());
		 * labTestSampleDto.setPreArtNumber(d.getPreArtNumber()); if
		 * (!CollectionUtils.isEmpty(d.getArtPatientAssessments())) {
		 * d.getArtPatientAssessments().forEach(pa -> { if (pa.getIsDelete() !=
		 * Boolean.FALSE) { labTestSampleDto.setBeneficiaryHivStatus(pa.getHivStatus());
		 * } }); } } }); }
		 */

		/*
		 * s.getBeneficiary().getArtBeneficiaryDetails().forEach(a -> { if
		 * (a.getIsDelete() == Boolean.FALSE) { labTestSampleDto.setArtId(a.getId());
		 * labTestSampleDto.setArtNo(a.getArtCentreCode()); } });
		 */

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
		labTestSampleDto.setErrorCode(s.getErrorCode());

		if (s.getDispatchedToLab() != null && s.getDispatchedToLab().getMachine() != null) {
			labTestSampleDto.setTestMachineId(s.getDispatchedToLab().getMachine().getId());
			labTestSampleDto.setTestMachine(s.getDispatchedToLab().getMachine().getMachineName());
		}

		if (s.getAuthorizer() != null) {
			labTestSampleDto.setAuthorizerId(s.getAuthorizer().getId());
			labTestSampleDto.setAuthorizerName(LabServiceUtil.getUserName(s.getAuthorizer()));
			labTestSampleDto.setAuthorizerSignature(s.getAuthorizerSignature());
		}

		labTestSampleDto.setTypeOfSpecimen(s.getTypeOfSpecimen());
		if (s.getLabTecnician() != null) {
			labTestSampleDto.setLabTechnicianId(s.getLabTecnician().getId());
			labTestSampleDto.setLabTechnicianName(LabServiceUtil.getUserName(s.getLabTecnician()));
			labTestSampleDto.setLabTechnicianContact(s.getLabTecnician().getMobileNumber());
			labTestSampleDto.setLabTechnicianSignature(s.getLabTechnicianSignature());
		}
		if (s.getLabInCharge() != null) {
			labTestSampleDto.setLabInchargeId(s.getLabInCharge().getId());
			labTestSampleDto.setLabInchargeName(LabServiceUtil.getUserName(s.getLabInCharge()));
			labTestSampleDto.setLabInChargeContact(s.getLabInCharge().getMobileNumber());
			labTestSampleDto.setLabInchargeSignature(s.getLabInchargeSignature());
		}
		labTestSampleDto.setTestRequestFormLink(s.getTestRequestFormLink());
		return labTestSampleDto;
	}

}
