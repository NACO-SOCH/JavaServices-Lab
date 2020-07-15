package gov.naco.soch.lab.util;

import gov.naco.soch.entity.Beneficiary;
import gov.naco.soch.entity.UserMaster;

public class LabServiceUtil {

	public static String getUserName(UserMaster user) {

		String firstName = user.getFirstname() != null ? user.getFirstname().concat(" ") : "";
		String lastName = user.getLastname() != null ? user.getLastname() : "";
		String name = firstName + lastName;
		return name.replaceAll("\\s+", " ");
	}

	public static String getBeneficiaryName(Beneficiary beneficiary) {
		String firstName = beneficiary.getFirstName() != null ? beneficiary.getFirstName().concat(" ") : "";
//		String middleName = beneficiary.getMiddleName() != null ? beneficiary.getMiddleName().concat(" ") : "";
		String lastName = beneficiary.getLastName() != null ? beneficiary.getLastName() : "";
		String name = firstName + lastName;
		return name.replaceAll("\\s+", " ");
	}
}
