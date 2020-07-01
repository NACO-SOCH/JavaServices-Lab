package gov.naco.soch.lab.mapper;

import java.time.LocalDate;
import java.time.Period;

import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.lab.dto.ResultDto;

public class MHLMapper {

	public static ResultDto mapLabTestSampleToResponseDto(LabTestSample labTestSample) {
		ResultDto result = new ResultDto();
		if (labTestSample.getBeneficiary() != null) {
			result.setArt_num(labTestSample.getBeneficiary().getArtNumber());
			result.setGender(labTestSample.getBeneficiary().getGenderId().getName());
			result.setPatient_name(
					labTestSample.getBeneficiary().getFirstName() + " " + labTestSample.getBeneficiary().getLastName());
			result.setPre_art_num(labTestSample.getBeneficiary().getPreArtNumber());
			LocalDate dateOfBirth = labTestSample.getBeneficiary().getDateOfBirth();
			LocalDate currentDate = LocalDate.now();
			LocalDate birthday = LocalDate.of(dateOfBirth.getYear(), dateOfBirth.getMonth(),
					dateOfBirth.getDayOfMonth());
			Period p = Period.between(birthday, currentDate);
			result.setAge(p.getYears() + " Years " + p.getMonths() + " Months ");
			result.setBirth_date(dateOfBirth);
		}
		if (labTestSample.getSampleCollectedFacility() != null) {
			result.setClient_code(labTestSample.getSampleCollectedFacility().getCode());
		}
		result.setBarcode(labTestSample.getBarcodeNumber());
		if (labTestSample.getSampleCollectedDate() != null) {
			result.setCollection_date(labTestSample.getSampleCollectedDate().toLocalDate());
		}
		if (labTestSample.getSampleDispatchDate() != null) {
			result.setDispatch_date(labTestSample.getSampleDispatchDate().toLocalDate());
		}
		if (labTestSample.getMasterSampleStatus() != null) {
			result.setSample_status(labTestSample.getMasterSampleStatus().getStatus());
		}
		if (labTestSample.getTestType() != null) {
			result.setTest_type(labTestSample.getTestType().getTestType());
		}
		return result;
	}

}
