package gov.naco.soch.lab.dto;

import java.util.List;

public class PatientLoadResponseDto {

	private Integer code;
	private Boolean success;
	private String msg;
	private List<ClientDetailsDto> result;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<ClientDetailsDto> getResult() {
		return result;
	}

	public void setResult(List<ClientDetailsDto> result) {
		this.result = result;
	}

}
