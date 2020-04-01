package gov.naco.soch.lab.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class VLTestResultDto {

	private Long batchId;
	private Long sampleId;
	private String bdnSerialNumber;
	private Long artcId;
	private String artcName;
	private String artcAddress;
	private Long beneficiaryId;
	private String beneficiaryUid;
	private String beneficiaryName;
	private LocalDate beneficiaryDob;
	private String beneficiaryAge;
	private String beneficiaryGender;
	private String beneficiaryHivStatus;
	private String barcodeNumber;
	private String artNumber;
	private String preArtNumber;
	private LocalDateTime sampleCollectedDate;
	private LocalDateTime sampleReceivedDate;
	private Long testTypeId;
	private String testType;
	private Long resultStatusId;
	private String resultStatus;
	private LocalDateTime resultReceivedDate;
	private Long resultTypeId;
	private String resultType;
	private String resultValue;
	private Long labId;
	private String labName;
	private String labAddress;
	private String typeOfSpecimen;
	private String logValue;
	private Boolean isError;
	private Long errorCode;

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getSampleId() {
		return sampleId;
	}

	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}

	public String getBdnSerialNumber() {
		return bdnSerialNumber;
	}

	public void setBdnSerialNumber(String bdnSerialNumber) {
		this.bdnSerialNumber = bdnSerialNumber;
	}

	public Long getArtcId() {
		return artcId;
	}

	public void setArtcId(Long artcId) {
		this.artcId = artcId;
	}

	public String getArtcName() {
		return artcName;
	}

	public void setArtcName(String artcName) {
		this.artcName = artcName;
	}

	public String getArtcAddress() {
		return artcAddress;
	}

	public void setArtcAddress(String artcAddress) {
		this.artcAddress = artcAddress;
	}

	public Long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryUid() {
		return beneficiaryUid;
	}

	public void setBeneficiaryUid(String beneficiaryUid) {
		this.beneficiaryUid = beneficiaryUid;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public LocalDate getBeneficiaryDob() {
		return beneficiaryDob;
	}

	public void setBeneficiaryDob(LocalDate beneficiaryDob) {
		this.beneficiaryDob = beneficiaryDob;
	}

	public String getBeneficiaryAge() {
		return beneficiaryAge;
	}

	public void setBeneficiaryAge(String beneficiaryAge) {
		this.beneficiaryAge = beneficiaryAge;
	}

	public String getBeneficiaryGender() {
		return beneficiaryGender;
	}

	public void setBeneficiaryGender(String beneficiaryGender) {
		this.beneficiaryGender = beneficiaryGender;
	}

	public String getBeneficiaryHivStatus() {
		return beneficiaryHivStatus;
	}

	public void setBeneficiaryHivStatus(String beneficiaryHivStatus) {
		this.beneficiaryHivStatus = beneficiaryHivStatus;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}

	public String getArtNumber() {
		return artNumber;
	}

	public void setArtNumber(String artNumber) {
		this.artNumber = artNumber;
	}

	public String getPreArtNumber() {
		return preArtNumber;
	}

	public void setPreArtNumber(String preArtNumber) {
		this.preArtNumber = preArtNumber;
	}

	public LocalDateTime getSampleCollectedDate() {
		return sampleCollectedDate;
	}

	public void setSampleCollectedDate(LocalDateTime sampleCollectedDate) {
		this.sampleCollectedDate = sampleCollectedDate;
	}

	public LocalDateTime getSampleReceivedDate() {
		return sampleReceivedDate;
	}

	public void setSampleReceivedDate(LocalDateTime sampleReceivedDate) {
		this.sampleReceivedDate = sampleReceivedDate;
	}

	public Long getTestTypeId() {
		return testTypeId;
	}

	public void setTestTypeId(Long testTypeId) {
		this.testTypeId = testTypeId;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public Long getResultStatusId() {
		return resultStatusId;
	}

	public void setResultStatusId(Long resultStatusId) {
		this.resultStatusId = resultStatusId;
	}

	public String getResultStatus() {
		return resultStatus;
	}

	public void setResultStatus(String resultStatus) {
		this.resultStatus = resultStatus;
	}

	public LocalDateTime getResultReceivedDate() {
		return resultReceivedDate;
	}

	public void setResultReceivedDate(LocalDateTime resultReceivedDate) {
		this.resultReceivedDate = resultReceivedDate;
	}

	public Long getResultTypeId() {
		return resultTypeId;
	}

	public void setResultTypeId(Long resultTypeId) {
		this.resultTypeId = resultTypeId;
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

	public Long getLabId() {
		return labId;
	}

	public void setLabId(Long labId) {
		this.labId = labId;
	}

	public String getLabName() {
		return labName;
	}

	public void setLabName(String labName) {
		this.labName = labName;
	}

	public String getLabAddress() {
		return labAddress;
	}

	public void setLabAddress(String labAddress) {
		this.labAddress = labAddress;
	}

	public String getTypeOfSpecimen() {
		return typeOfSpecimen;
	}

	public void setTypeOfSpecimen(String typeOfSpecimen) {
		this.typeOfSpecimen = typeOfSpecimen;
	}

	public String getLogValue() {
		return logValue;
	}

	public void setLogValue(String logValue) {
		this.logValue = logValue;
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
