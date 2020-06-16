package gov.naco.soch.lab.dto;

import java.time.LocalDate;

public class TestHistoryDto {

	private LocalDate visitDate;
	private String ageOnTest;
	private String feedingType;
	private String testType;
	private String result;
	private LocalDate resultDate;
	private String resultStatus;
	private String labName;
	private String barcodeNumber;
	private LocalDate beneficiaryDob;

	public LocalDate getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(LocalDate visitDate) {
		this.visitDate = visitDate;
	}

	public String getAgeOnTest() {
		return ageOnTest;
	}

	public void setAgeOnTest(String ageOnTest) {
		this.ageOnTest = ageOnTest;
	}

	public String getFeedingType() {
		return feedingType;
	}

	public void setFeedingType(String feedingType) {
		this.feedingType = feedingType;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public LocalDate getResultDate() {
		return resultDate;
	}

	public void setResultDate(LocalDate resultDate) {
		this.resultDate = resultDate;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public LocalDate getBeneficiaryDob() {
		return beneficiaryDob;
	}

	public void setBeneficiaryDob(LocalDate beneficiaryDob) {
		this.beneficiaryDob = beneficiaryDob;
	}

}
