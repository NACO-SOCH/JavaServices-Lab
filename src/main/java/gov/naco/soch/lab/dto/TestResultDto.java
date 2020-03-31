package gov.naco.soch.lab.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TestResultDto implements Serializable {


	private static final long serialVersionUID = 1L;

	private String barCode;
	private LocalDateTime samplCollectedDate;
	private LocalDateTime sampleReceivedDate;	
	private LocalDateTime sampleDispatchedDate;	

	private String beneficiaryUId;
	private String beneficiaryName;
	private String beneficiaryAge;
	private String beneficiaryHivStatus;
	private String beneficiaryGender;


	private String artCName;
	private String testName;
	private Long artNo;
	private Long noOfPatients;	
	private String testType;

	private String bdnSerialNo;
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	public LocalDateTime getSamplCollectedDate() {
		return samplCollectedDate;
	}
	public void setSamplCollectedDate(LocalDateTime samplCollectedDate) {
		this.samplCollectedDate = samplCollectedDate;
	}
	public LocalDateTime getSampleReceivedDate() {
		return sampleReceivedDate;
	}
	public void setSampleReceivedDate(LocalDateTime sampleReceivedDate) {
		this.sampleReceivedDate = sampleReceivedDate;
	}
	public LocalDateTime getSampleDispatchedDate() {
		return sampleDispatchedDate;
	}
	public void setSampleDispatchedDate(LocalDateTime sampleDispatchedDate) {
		this.sampleDispatchedDate = sampleDispatchedDate;
	}
	public String getBeneficiaryUId() {
		return beneficiaryUId;
	}
	public void setBeneficiaryUId(String beneficiaryUId) {
		this.beneficiaryUId = beneficiaryUId;
	}
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	public String getBeneficiaryAge() {
		return beneficiaryAge;
	}
	public void setBeneficiaryAge(String beneficiaryAge) {
		this.beneficiaryAge = beneficiaryAge;
	}
	public String getBeneficiaryHivStatus() {
		return beneficiaryHivStatus;
	}
	public void setBeneficiaryHivStatus(String beneficiaryHivStatus) {
		this.beneficiaryHivStatus = beneficiaryHivStatus;
	}
	public String getBeneficiaryGender() {
		return beneficiaryGender;
	}
	public void setBeneficiaryGender(String beneficiaryGender) {
		this.beneficiaryGender = beneficiaryGender;
	}
	public String getArtCName() {
		return artCName;
	}
	public void setArtCName(String artCName) {
		this.artCName = artCName;
	}
	public String getTestName() {
		return testName;
	}
	public void setTestName(String testName) {
		this.testName = testName;
	}
	public Long getArtNo() {
		return artNo;
	}
	public void setArtNo(Long artNo) {
		this.artNo = artNo;
	}
	public Long getNoOfPatients() {
		return noOfPatients;
	}
	public void setNoOfPatients(Long noOfPatients) {
		this.noOfPatients = noOfPatients;
	}
	public String getTestType() {
		return testType;
	}
	public void setTestType(String testType) {
		this.testType = testType;
	}	
	public String getBdnSerialNo() {
		return bdnSerialNo;
	}
	public void setBdnSerialNo(String bdnSerialNo) {
		this.bdnSerialNo = bdnSerialNo;
	}


}


