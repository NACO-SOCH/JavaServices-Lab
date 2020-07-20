package gov.naco.soch.lab.dto;

public class RecordResultDto {

	private String auth_key;
	private String barcode;
	private String result_value;

	public String getAuth_key() {
		return auth_key;
	}

	public void setAuth_key(String auth_key) {
		this.auth_key = auth_key;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getResult_value() {
		return result_value;
	}

	public void setResult_value(String result_value) {
		this.result_value = result_value;
	}

}
