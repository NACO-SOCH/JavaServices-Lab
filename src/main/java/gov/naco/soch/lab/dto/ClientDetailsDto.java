package gov.naco.soch.lab.dto;

import java.time.LocalDate;

public class ClientDetailsDto extends ResponseDto{

	private String STATE;
	private String DISTRICT;
	private String CLIENT_CODE;
	private String CLIENT_NAME;
	private LocalDate DATE;
	private Integer PATIENT_LOAD;

	public String getSTATE() {
		return STATE;
	}

	public void setSTATE(String STATE) {
		this.STATE = STATE;
	}

	public String getDISTRICT() {
		return DISTRICT;
	}

	public void setDISTRICT(String DISTRICT) {
		this.DISTRICT = DISTRICT;
	}

	public String getCLIENT_CODE() {
		return CLIENT_CODE;
	}

	public void setCLIENT_CODE(String CLIENT_CODE) {
		this.CLIENT_CODE = CLIENT_CODE;
	}

	public String getCLIENT_NAME() {
		return CLIENT_NAME;
	}

	public void setCLIENT_NAME(String CLIENT_NAME) {
		CLIENT_NAME = this.CLIENT_NAME;
	}

	public LocalDate getDATE() {
		return DATE;
	}

	public void setDATE(LocalDate DATE) {
		this.DATE = DATE;
	}

	public Integer getPATIENT_LOAD() {
		return PATIENT_LOAD;
	}

	public void setPATIENT_LOAD(Integer PATIENT_LOAD) {
		this.PATIENT_LOAD = PATIENT_LOAD;
	}

}
