package gov.naco.soch.lab.dto;

public class RecordBatchResultDto {

	private String barcode;
	private String resultValue;
	private String logValue;
	private Boolean isResultRecorded = Boolean.FALSE;
	private String resultDate;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getResultValue() {
		return resultValue;
	}

	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}

	public String getLogValue() {
		return logValue;
	}

	public void setLogValue(String logValue) {
		this.logValue = logValue;
	}

	public Boolean getIsResultRecorded() {
		return isResultRecorded;
	}

	public void setIsResultRecorded(Boolean isResultRecorded) {
		this.isResultRecorded = isResultRecorded;
	}

	public String getResultDate() {
		return resultDate;
	}

	public void setResultDate(String resultDate) {
		this.resultDate = resultDate;
	}

	@Override
	public String toString() {
		return "RecordBatchResultDto [barcode=" + barcode + ", resultValue=" + resultValue + ", logValue=" + logValue
				+ ", isResultRecorded=" + isResultRecorded + ", resultDate=" + resultDate + "]";
	}

}
