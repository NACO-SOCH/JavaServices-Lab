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


	private String artcName;
	//private String testName;
	private Long artNo;
	private Long noOfPatients;	
	private String testType;
	private String resultStatus;	
	private String bdnSerialNo;
	private Long resultStatusId;
	private Long testTypeId;
	private Long batchId;
	//
	private String address;		
	private LocalDateTime resultDate;	
	private String  resultType;;
	private String  resultValue;
	private String  logvalue;
	private Boolean isError;
	private Long errorCode;
	
	
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
	public String getArtcName() {
		return artcName;
	}
	public void setArtcName(String artcName) {
		this.artcName = artcName;
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
	public String getResultStatus() {
		return resultStatus;
	}
	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}
	public String getBdnSerialNo() {
		return bdnSerialNo;
	}
	public void setBdnSerialNo(String bdnSerialNo) {
		this.bdnSerialNo = bdnSerialNo;
	}
	public Long getResultStatusId() {
		return resultStatusId;
	}
	public void setResultStatusId(Long resultStatusId) {
		this.resultStatusId = resultStatusId;
	}
	public Long getTestTypeId() {
		return testTypeId;
	}
	public void setTestTypeId(Long testTypeId) {
		this.testTypeId = testTypeId;
	}
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public LocalDateTime getResultDate() {
		return resultDate;
	}
	public void setResultDate(LocalDateTime resultDate) {
		this.resultDate = resultDate;
	}
	public String getResultType() {
		return resultType;
	}
	public void setResultType(String resultType) {
		this.resultType = resultType;
	}
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	public String getLogvalue() {
		return logvalue;
	}
	public void setLogvalue(String logvalue) {
		this.logvalue = logvalue;
	}
	public Boolean getIsError() {
		return isError;
	}
	public void setIsError(Boolean isError) {
		this.isError = isError;
	}
	public Long getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Long errorCode) {
		this.errorCode = errorCode;
	}
	
	
	
}


