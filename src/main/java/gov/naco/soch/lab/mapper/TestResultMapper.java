package gov.naco.soch.lab.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
		String facAddressString = (facAddress.getAddressLineOne() != null ? facAddress.getAddressLineOne() : "")
				+ (facAddress.getAddressLineTwo() != null ? ", " + facAddress.getAddressLineTwo() : "");

		vlTestResultDto.setArtcAddress(facAddressString);
		vlTestResultDto.setArtcCode(labTestSample.getLabTestSampleBatch().getFacility().getCode());
		vlTestResultDto.setArtcArtCode(labTestSample.getLabTestSampleBatch().getFacility().getArtcode());
		vlTestResultDto.setLabId(labTestSample.getLabTestSampleBatch().getLab().getId());
		vlTestResultDto.setLabName(labTestSample.getLabTestSampleBatch().getLab().getName());

		Address labAddress = labTestSample.getLabTestSampleBatch().getLab().getAddress();
		String labAddressString = (labAddress.getAddressLineOne() != null ? labAddress.getAddressLineOne() : "")
				+ (labAddress.getAddressLineTwo() != null ? ", " + labAddress.getAddressLineTwo() : "");
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
		} else {
			vlTestResultDto.setBeneficiaryGender(labTestSample.getBeneficiary().getGender());
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

		return vlTestResultDto;
	}

	public static List<BeneficiaryIctcStatusTracking> mappingStatuses(List<LabTestSample> labTestSampleList) {
		Integer currentStatus = 0;
		List<BeneficiaryIctcStatusTracking> trackingList = new ArrayList<BeneficiaryIctcStatusTracking>();
		LoginResponseDto loginResponseDto = UserUtils.getLoggedInUserDetails();
		if (labTestSampleList != null) {
			for (LabTestSample sample : labTestSampleList) {
				if (sample.getTestType().getId() == 5 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 3) {
					currentStatus = 11; // S-DBS-1- approved
				} else if (sample.getTestType().getId() == 5 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 5) {
					currentStatus = 12; // S-DBS-1- rejected
				} else if (sample.getTestType().getId() == 5 && sample.getMasterSampleStatus().getId() == 2
						&& sample.getMasterResultStatus().getId() == 1) {
					currentStatus = 12; // S-DBS-1- rejected on receiving samples	
				}else if (sample.getTestType().getId() == 5 && sample.getIsError() == true) {
					currentStatus = 13; // S-DBS-1- error
						
				}else if (sample.getTestType().getId() == 6 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 3) {
					currentStatus = 17; // S-DBS-11- approved
				} else if (sample.getTestType().getId() == 6 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 5) {
					currentStatus = 18; // S-DBS-11- rejected
				} else if (sample.getTestType().getId() == 6 && sample.getMasterSampleStatus().getId() == 2
						&& sample.getMasterResultStatus().getId() == 1) {
					currentStatus = 18; // S-DBS-11- rejected on receiving samples	
				}else if (sample.getTestType().getId() == 6 && sample.getIsError() == true) {
					currentStatus = 19; // S-DBS-11- error
					
				}else if (sample.getTestType().getId() == 7 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 3) {
					currentStatus = 23; // S-DBS-111- approved
				} else if (sample.getTestType().getId() == 7 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 5) {
					currentStatus = 24; // S-DBS-111- rejected
				} else if (sample.getTestType().getId() == 7 && sample.getMasterSampleStatus().getId() == 2
						&& sample.getMasterResultStatus().getId() == 1) {
					currentStatus = 24; // S-DBS-111- rejected on receiving samples		
				}else if (sample.getTestType().getId() == 7 && sample.getIsError() == true) {
					currentStatus = 25; // S-DBS-111- error
					
				}else if (sample.getTestType().getId() == 8 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 3) {
					currentStatus = 29; // C-DBS-1- approved
				} else if (sample.getTestType().getId() == 8 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 5) {
					currentStatus = 30; // C-DBS-1- rejected
				} else if (sample.getTestType().getId() == 8 && sample.getMasterSampleStatus().getId() == 2
						&& sample.getMasterResultStatus().getId() == 1) {
					currentStatus = 30; // C-DBS-1- rejected on receiving samples		
				}else if (sample.getTestType().getId() == 8 && sample.getIsError() == true) {
					currentStatus = 31; // C-DBS-1- error
					
				}else if (sample.getTestType().getId() == 9 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 3) {
					currentStatus = 35; // C-DBS-11- approved
				} else if (sample.getTestType().getId() == 9 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 5) {
					currentStatus = 36; // C-DBS-11- rejected
				} else if (sample.getTestType().getId() == 9 && sample.getMasterSampleStatus().getId() == 2
						&& sample.getMasterResultStatus().getId() == 1) {
					currentStatus = 36; // C-DBS-11- rejected on receiving samples	
				}else if (sample.getTestType().getId() == 9 && sample.getIsError() == true) {
					currentStatus = 37; // C-DBS-11- error
					
				}else if (sample.getTestType().getId() == 10 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 3) {
					currentStatus = 41; // C-DBS-111- approved
				} else if (sample.getTestType().getId() == 10 && sample.getMasterSampleStatus().getId() == 4
						&& sample.getMasterResultStatus().getId() == 5) {
					currentStatus = 42; // C-DBS-111- rejected
				} else if (sample.getTestType().getId() == 10 && sample.getMasterSampleStatus().getId() == 2
						&& sample.getMasterResultStatus().getId() == 1) {
					currentStatus = 42; // C-DBS-111- rejected on receiving samples	
				}else if (sample.getTestType().getId() == 10 && sample.getIsError() == true) {
					currentStatus = 43; // C-DBS-111- error
				}
				
				BeneficiaryIctcStatusTracking tracker = new BeneficiaryIctcStatusTracking();
				tracker.setBeneficiaryId(sample.getBeneficiary().getId());
			//	tracker.setCreatedBy(sample.getCreatedBy().intValue());
				tracker.setFacilityId(sample.getLabTestSampleBatch().getFacility().getId());
				tracker.setIsActive(true);
				tracker.setIsDeleted(false);
				tracker.setStatusChangedDate(LocalDateTime.now());
				tracker.setStatusChangedBy(loginResponseDto.getUserId().intValue());
				tracker.setCurrentIctcBeneficiaryStatusId(currentStatus);
				tracker.setCreatedTime(LocalDateTime.now());
				trackingList.add(tracker);

			}
		}
		
		return trackingList;
		
	}

}
