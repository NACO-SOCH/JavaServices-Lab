package gov.naco.soch.lab.dto;

import java.time.LocalDate;
import java.util.List;

public class PatientLoadDto {

	private List<LocalDate> localDate;
	private List<String> clientCode;

	public List<LocalDate> getLocalDate() {
		return localDate;
	}

	public void setLocalDate(List<LocalDate> localDate) {
		this.localDate = localDate;
	}

	public List<String> getClientCode() {
		return clientCode;
	}

	public void setClientCode(List<String> clientCode) {
		this.clientCode = clientCode;
	}

}
