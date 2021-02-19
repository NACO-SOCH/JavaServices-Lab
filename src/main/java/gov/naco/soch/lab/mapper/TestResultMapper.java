package gov.naco.soch.lab.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.dto.LoginResponseDto;
import gov.naco.soch.entity.Address;
import gov.naco.soch.entity.ArtBeneficiary;
import gov.naco.soch.entity.BeneficiaryIctcStatusTracking;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.util.LabServiceUtil;
import gov.naco.soch.util.UserUtils;

public class TestResultMapper {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public static TestResultDto mapToTestResultDto(LabTestSample labTestSample) {
		TestResultDto vlTestResultDto = new TestResultDto();

		vlTestResultDto.setBatchId(labTestSample.getLabTestSampleBatch().getId());
		vlTestResultDto.setSampleId(labTestSample.getId());
		vlTestResultDto.setBdnSerialNumber(labTestSample.getLabTestSampleBatch().getBdnSerialNumber());
		vlTestResultDto.setArtcId(labTestSample.getLabTestSampleBatch().getFacility().getId());
		vlTestResultDto.setArtcName(labTestSample.getLabTestSampleBatch().getFacility().getName());

		Address facAddress = labTestSample.getLabTestSampleBatch().getFacility().getAddress();
		String facAddressString = (facAddress.getAddressLineOne() != null ? facAddress.getAddressLineOne() : "");
		if(facAddress.getAddressLineTwo() != null && !StringUtils.isBlank(facAddress.getAddressLineTwo())) {
			facAddressString = facAddressString	+ (facAddress.getAddressLineTwo() != null ? ", " + facAddress.getAddressLineTwo() : "");
		}
		if (facAddress.getTown() != null) {
			facAddressString = facAddressString
					+ (facAddress.getTown().getTownName() != null ? ", " + facAddress.getTown().getTownName() : "");
		}
		if (facAddress.getSubdistrict() != null) {
			facAddressString = facAddressString + (facAddress.getSubdistrict().getSubdistrictName() != null
					? ", " + facAddress.getSubdistrict().getSubdistrictName()
					: "");
		}
		if (facAddress.getDistrict() != null) {
			facAddressString = facAddressString
					+ (facAddress.getDistrict().getName() != null ? ", " + facAddress.getDistrict().getName() : "");
		}
		if (facAddress.getState() != null) {
			facAddressString = facAddressString
					+ (facAddress.getState().getName() != null ? ", " + facAddress.getState().getName() : "");
		}
		if (facAddress.getPincodeEntity() != null) {
			facAddressString = facAddressString
					+ (facAddress.getPincodeEntity().getPincode() != null ? ", " + facAddress.getPincodeEntity().getPincode() : "");
		}

		vlTestResultDto.setArtcAddress(facAddressString);
		vlTestResultDto.setArtcCode(labTestSample.getLabTestSampleBatch().getFacility().getCode());
		vlTestResultDto.setArtcArtCode(labTestSample.getLabTestSampleBatch().getFacility().getArtcode());
		vlTestResultDto.setLabId(labTestSample.getLabTestSampleBatch().getLab().getId());
		vlTestResultDto.setLabName(labTestSample.getLabTestSampleBatch().getLab().getName());

		Address labAddress = labTestSample.getLabTestSampleBatch().getLab().getAddress();
		String labAddressString = (labAddress.getAddressLineOne() != null ? labAddress.getAddressLineOne() : "");
		if(labAddress.getAddressLineTwo() != null && !StringUtils.isBlank(labAddress.getAddressLineTwo())) {
			labAddressString = labAddressString	+ (labAddress.getAddressLineTwo() != null ? ", " + labAddress.getAddressLineTwo() : "");
		}
		if (labAddress.getTown() != null) {
			labAddressString = labAddressString
					+ (labAddress.getTown().getTownName() != null ? ", " + labAddress.getTown().getTownName() : "");
		}
		if (labAddress.getSubdistrict() != null) {
			labAddressString = labAddressString + (labAddress.getSubdistrict().getSubdistrictName() != null
					? ", " + labAddress.getSubdistrict().getSubdistrictName()
					: "");
		}
		if (labAddress.getDistrict() != null) {
			labAddressString = facAddressString
					+ (labAddress.getDistrict().getName() != null ? ", " + labAddress.getDistrict().getName() : "");
		}
		if (labAddress.getState() != null) {
			labAddressString = facAddressString
					+ (labAddress.getState().getName() != null ? ", " + labAddress.getState().getName() : "");
		}
		if (labAddress.getPincodeEntity() != null) {
			labAddressString = labAddressString
					+ (labAddress.getPincodeEntity().getPincode() != null ? ", " + labAddress.getPincodeEntity().getPincode() : "");
		}
				
		if (!labAddressString.equals(", ")) {
			vlTestResultDto.setLabAddress(labAddressString);
		}
		vlTestResultDto.setLabCode(labTestSample.getLabTestSampleBatch().getLab().getCode());

		vlTestResultDto.setDispatchDate(labTestSample.getLabTestSampleBatch().getDispatchDate());
		vlTestResultDto.setReceivedDate(labTestSample.getLabTestSampleBatch().getReceivedDate());
		vlTestResultDto.setNum_ofSamples(labTestSample.getLabTestSampleBatch().getNumOfSamples());
		vlTestResultDto.setAcceptedSamples(labTestSample.getLabTestSampleBatch().getAcceptedSamples());
		vlTestResultDto.setRejectedSamples(labTestSample.getLabTestSampleBatch().getRejectedSamples());

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

		if (labTestSample.getBeneficiary().getGenderId() != null) {
			vlTestResultDto.setBeneficiaryGender(labTestSample.getBeneficiary().getGenderId().getName());
		} 

		vlTestResultDto.setArtNumber(labTestSample.getBeneficiary().getArtNumber());
		vlTestResultDto.setPreArtNumber(labTestSample.getBeneficiary().getPreArtNumber());
		vlTestResultDto.setBarcodeNumber(labTestSample.getBarcodeNumber());
		vlTestResultDto.setIctcDnaCode(labTestSample.getLabTestSampleBatch().getFacility().getCode());

		if (!CollectionUtils.isEmpty(labTestSample.getBeneficiary().getArtBeneficiary())) {
			if (labTestSample.getBeneficiary().getArtBeneficiary().iterator().hasNext()) {

				ArtBeneficiary artDetails = labTestSample.getBeneficiary().getArtBeneficiary().iterator().next();
				if (artDetails.getMasterArtBeneficiaryStatus() != null) {
					vlTestResultDto.setBeneficiaryHivStatus(artDetails.getMasterArtBeneficiaryStatus().getName());
				}
				if (artDetails.getMasterRiskFactor() != null) {
					vlTestResultDto.setPopulationType(artDetails.getMasterRiskFactor().getName());
				}
			}
		}

		if (labTestSample.getDispatchedToLab() != null && labTestSample.getDispatchedToLab().getMachine() != null) {
			vlTestResultDto.setTestMachineId(labTestSample.getDispatchedToLab().getMachine().getId());
			vlTestResultDto.setTestMachineName(labTestSample.getDispatchedToLab().getMachine().getMachineName());
		}

		/*
		 * if (!CollectionUtils.isEmpty(labTestSample.getBeneficiary().
		 * getArtBeneficiaryDetails())) {
		 * 
		 * labTestSample.getBeneficiary().getArtBeneficiaryDetails().forEach(d -> { if
		 * (d.getIsDelete() == Boolean.FALSE) {
		 * vlTestResultDto.setArtNumber(d.getArtNumber());
		 * vlTestResultDto.setPreArtNumber(d.getPreArtNumber()); if
		 * (!CollectionUtils.isEmpty(d.getArtPatientAssessments())) {
		 * d.getArtPatientAssessments().forEach(pa -> { if (pa.getIsDelete() !=
		 * Boolean.FALSE) { vlTestResultDto.setBeneficiaryHivStatus(pa.getHivStatus());
		 * } }); } } }); }
		 */

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
		
		if(labTestSample.getResultApprovedDate() != null) {
			vlTestResultDto.setResultApprovedDate(labTestSample.getResultApprovedDate().format(formatter));
		}

		return vlTestResultDto;
	}

	public static BeneficiaryIctcStatusTracking mappingStatuses(LabTestSample sample, Integer previouStatus) {
		Integer currentStatus = 0;
		List<BeneficiaryIctcStatusTracking> trackingList = new ArrayList<BeneficiaryIctcStatusTracking>();
		LoginResponseDto loginResponseDto = UserUtils.getLoggedInUserDetails();
		BeneficiaryIctcStatusTracking tracker = new BeneficiaryIctcStatusTracking();
		if (sample != null) {

			if (sample.getTestType().getId() == 5 && sample.getMasterSampleStatus().getId() == 1
					&& sample.getMasterResultStatus().getId() == 2 && sample.getIsError() == false) {
				currentStatus = 10; // S-DBS-1- awaiting approval
			} else if (sample.getTestType().getId() == 5 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 3 && sample.getIsError() == false) {
				currentStatus = 11; // S-DBS-1- approved
			} else if (sample.getTestType().getId() == 5 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 5) {
				currentStatus = 12; // S-DBS-1- rejected
			} else if (sample.getTestType().getId() == 5 && sample.getIsError() == true) {
				currentStatus = 13; // S-DBS-1- error

			} else if (sample.getTestType().getId() == 6 && sample.getMasterSampleStatus().getId() == 1
					&& sample.getMasterResultStatus().getId() == 2 && sample.getIsError() == false) {
				currentStatus = 16; // S-DBS-11- awaiting approval
			} else if (sample.getTestType().getId() == 6 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 3 && sample.getIsError() == false) {
				currentStatus = 17; // S-DBS-11- approved
			} else if (sample.getTestType().getId() == 6 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 5) {
				currentStatus = 18; // S-DBS-11- rejected
			} else if (sample.getTestType().getId() == 6 && sample.getIsError() == true) {
				currentStatus = 19; // S-DBS-11- error

			} else if (sample.getTestType().getId() == 7 && sample.getMasterSampleStatus().getId() == 1
					&& sample.getMasterResultStatus().getId() == 2 && sample.getIsError() == false) {
				currentStatus = 22; // S-DBS-111- awaiting approval
			} else if (sample.getTestType().getId() == 7 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 3 && sample.getIsError() == false) {
				currentStatus = 23; // S-DBS-111- approved
			} else if (sample.getTestType().getId() == 7 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 5) {
				currentStatus = 24; // S-DBS-111- rejected
			} else if (sample.getTestType().getId() == 7 && sample.getIsError() == true) {
				currentStatus = 25; // S-DBS-111- error

			} else if (sample.getTestType().getId() == 8 && sample.getMasterSampleStatus().getId() == 1
					&& sample.getMasterResultStatus().getId() == 2 && sample.getIsError() == false) {
				currentStatus = 28; // C-DBS-1- awaiting approval
			} else if (sample.getTestType().getId() == 8 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 3 && sample.getIsError() == false) {
				currentStatus = 29; // C-DBS-1- approved
			} else if (sample.getTestType().getId() == 8 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 5) {
				currentStatus = 30; // C-DBS-1- rejected
			} else if (sample.getTestType().getId() == 8 && sample.getIsError() == true) {
				currentStatus = 31; // C-DBS-1- error

			} else if (sample.getTestType().getId() == 9 && sample.getMasterSampleStatus().getId() == 1
					&& sample.getMasterResultStatus().getId() == 2 && sample.getIsError() == false) {
				currentStatus = 34; // C-DBS-11- awaiting approval
			} else if (sample.getTestType().getId() == 9 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 3 && sample.getIsError() == false) {
				currentStatus = 35; // C-DBS-11- approved
			} else if (sample.getTestType().getId() == 9 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 5) {
				currentStatus = 36; // C-DBS-11- rejected
			} else if (sample.getTestType().getId() == 9 && sample.getIsError() == true) {
				currentStatus = 37; // C-DBS-11- error

			} else if (sample.getTestType().getId() == 10 && sample.getMasterSampleStatus().getId() == 1
					&& sample.getMasterResultStatus().getId() == 2 && sample.getIsError() == false) {
				currentStatus = 40; // C-DBS-111- awaiting approval
			} else if (sample.getTestType().getId() == 10 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 3 && sample.getIsError() == false) {
				currentStatus = 41; // C-DBS-111- approved
			} else if (sample.getTestType().getId() == 10 && sample.getMasterSampleStatus().getId() == 4
					&& sample.getMasterResultStatus().getId() == 5) {
				currentStatus = 42; // C-DBS-111- rejected
			} else if (sample.getTestType().getId() == 10 && sample.getIsError() == true) {
				currentStatus = 43; // C-DBS-111- error
			}

			tracker.setBeneficiaryId(sample.getBeneficiary().getId());
			tracker.setFacilityId(sample.getLabTestSampleBatch().getFacility().getId());
			tracker.setIsActive(true);
			tracker.setIsDeleted(false);
			tracker.setStatusChangedDate(LocalDateTime.now());
			tracker.setStatusChangedBy(loginResponseDto.getUserId().intValue());
			tracker.setCurrentIctcBeneficiaryStatusId(currentStatus);
			tracker.setPreviousIctcBeneficiaryStatusId(previouStatus);
			tracker.setCreatedTime(LocalDateTime.now());
			trackingList.add(tracker);

		}

		return tracker;

	}

}
