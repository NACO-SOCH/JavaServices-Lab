package gov.naco.soch.lab.dto;

import java.util.List;

public class PatientLoadDto {

	private String auth_key;
	private List<String> load_date;
	private List<String> client_code;

	public String getAuth_key() {
		return auth_key;
	}

	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}

	public List<String> getLoad_date() {
		return load_date;
	}

	public void setLoad_date(List<String> load_date) {
		this.load_date = load_date;
	}

	public List<String> getClient_code() {
		return client_code;
	}

	public void setClient_code(List<String> client_code) {
		this.client_code = client_code;
	}

}
