package gov.naco.soch.lab.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestResultDto {

	private Long batchId;
	private Long sampleId;
	private String bdnSerialNumber;
	private Long artcId;
	private String artcName;
	private String artcAddress;
	private String artcCode;
	private String artcArtCode;
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
	private String sampleDispatchDate;
	private String sampleCollectedDate;
	private String sampleReceivedDate;
	private Long sampleStatusId;
	private String sampleStatus;
	private Long testTypeId;
	private String testType;
	private Long resultStatusId;
	private String resultStatus;
	private String resultReceivedDate;
	private String resultDispatchDate;
	private Long resultTypeId;
	private String resultType;
	private String resultValue;
	private Long labId;
	private String labName;
	private String labAddress;
	private String labCode;
	private String typeOfSpecimen;
	private String logValue;
	private Boolean isError;
	private Long errorCode;
	private Long testMachineId;
	private String testMachineName;
	private Long labInChargeId;
	private String labInChargeName;
	private String labInChargeContact;
	private Long labTechnicianId;
	private String labTechnicianName;
	private String labTechnicianContact;
	private String populationType;
	private String department;

	private Long authorizerId;
	private String authorizerName;
	private String authorizerSignature;

	private Long artcLabTechId;
	private String artcLabTechName;
	private String artcLabTechContact;
	private LocalDateTime dispatchDate;
	private LocalDateTime receivedDate;
	private Long num_ofSamples;
	private Long acceptedSamples;
	private Long rejectedSamples;

	private String ictcDnaCode;
	private String infantDnaCode;
	private String infantPID;

	private Long motherId;
	private String motherName;
	private String motherUid;
	private String motherContact;
	private String motherAddress;
	private String motherArtNumber;
	private String motherPreArtNumber;

	private Boolean isPreviousTestDone;
	private String previousTestResult;
	private String labSpecimenId;
	private String feedingType;

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

	public String getArtcCode() {
		return artcCode;
	}

	public void setArtcCode(String artcCode) {
		this.artcCode = artcCode;
	}

	public String getArtcArtCode() {
		return artcArtCode;
	}

	public void setArtcArtCode(String artcArtCode) {
		this.artcArtCode = artcArtCode;
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

	public String getSampleDispatchDate() {
		return sampleDispatchDate;
	}

	public void setSampleDispatchDate(String sampleDispatchDate) {
		this.sampleDispatchDate = sampleDispatchDate;
	}

	public String getSampleCollectedDate() {
		return sampleCollectedDate;
	}

	public void setSampleCollectedDate(String sampleCollectedDate) {
		this.sampleCollectedDate = sampleCollectedDate;
	}

	public String getSampleReceivedDate() {
		return sampleReceivedDate;
	}

	public void setSampleReceivedDate(String sampleReceivedDate) {
		this.sampleReceivedDate = sampleReceivedDate;
	}

	public Long getSampleStatusId() {
		return sampleStatusId;
	}

	public void setSampleStatusId(Long sampleStatusId) {
		this.sampleStatusId = sampleStatusId;
	}

	public String getSampleStatus() {
		return sampleStatus;
	}

	public void setSampleStatus(String sampleStatus) {
		this.sampleStatus = sampleStatus;
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

	public String getResultReceivedDate() {
		return resultReceivedDate;
	}

	public void setResultReceivedDate(String resultReceivedDate) {
		this.resultReceivedDate = resultReceivedDate;
	}

	public String getResultDispatchDate() {
		return resultDispatchDate;
	}

	public void setResultDispatchDate(String resultDispatchDate) {
		this.resultDispatchDate = resultDispatchDate;
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

	public String getLabCode() {
		return labCode;
	}

	public void setLabCode(String labCode) {
		this.labCode = labCode;
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

	public Long getTestMachineId() {
		return testMachineId;
	}

	public void setTestMachineId(Long testMachineId) {
		this.testMachineId = testMachineId;
	}

	public String getTestMachineName() {
		return testMachineName;
	}

	public void setTestMachineName(String testMachineName) {
		this.testMachineName = testMachineName;
	}

	public Long getLabInChargeId() {
		return labInChargeId;
	}

	public void setLabInChargeId(Long labInChargeId) {
		this.labInChargeId = labInChargeId;
	}

	public String getLabInChargeName() {
		return labInChargeName;
	}

	public void setLabInChargeName(String labInChargeName) {
		this.labInChargeName = labInChargeName;
	}

	public String getLabInChargeContact() {
		return labInChargeContact;
	}

	public void setLabInChargeContact(String labInChargeContact) {
		this.labInChargeContact = labInChargeContact;
	}

	public Long getLabTechnicianId() {
		return labTechnicianId;
	}

	public void setLabTechnicianId(Long labTechnicianId) {
		this.labTechnicianId = labTechnicianId;
	}

	public String getLabTechnicianName() {
		return labTechnicianName;
	}

	public void setLabTechnicianName(String labTechnicianName) {
		this.labTechnicianName = labTechnicianName;
	}

	public String getLabTechnicianContact() {
		return labTechnicianContact;
	}

	public void setLabTechnicianContact(String labTechnicianContact) {
		this.labTechnicianContact = labTechnicianContact;
	}

	public String getPopulationType() {
		return populationType;
	}

	public void setPopulationType(String populationType) {
		this.populationType = populationType;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getAuthorizerId() {
		return authorizerId;
	}

	public void setAuthorizerId(Long authorizerId) {
		this.authorizerId = authorizerId;
	}

	public String getAuthorizerName() {
		return authorizerName;
	}

	public void setAuthorizerName(String authorizerName) {
		this.authorizerName = authorizerName;
	}

	public String getAuthorizerSignature() {
		return authorizerSignature;
	}

	public void setAuthorizerSignature(String authorizerSignature) {
		this.authorizerSignature = authorizerSignature;
	}

	public Long getArtcLabTechId() {
		return artcLabTechId;
	}

	public void setArtcLabTechId(Long artcLabTechId) {
		this.artcLabTechId = artcLabTechId;
	}

	public String getArtcLabTechName() {
		return artcLabTechName;
	}

	public void setArtcLabTechName(String artcLabTechName) {
		this.artcLabTechName = artcLabTechName;
	}

	public String getArtcLabTechContact() {
		return artcLabTechContact;
	}

	public void setArtcLabTechContact(String artcLabTechContact) {
		this.artcLabTechContact = artcLabTechContact;
	}

	public LocalDateTime getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(LocalDateTime dispatchDate) {
		this.dispatchDate = dispatchDate;
	}

	public LocalDateTime getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDateTime receivedDate) {
		this.receivedDate = receivedDate;
	}

	public Long getNum_ofSamples() {
		return num_ofSamples;
	}

	public void setNum_ofSamples(Long num_ofSamples) {
		this.num_ofSamples = num_ofSamples;
	}

	public Long getAcceptedSamples() {
		return acceptedSamples;
	}

	public void setAcceptedSamples(Long acceptedSamples) {
		this.acceptedSamples = acceptedSamples;
	}

	public Long getRejectedSamples() {
		return rejectedSamples;
	}

	public void setRejectedSamples(Long rejectedSamples) {
		this.rejectedSamples = rejectedSamples;
	}

	public String getIctcDnaCode() {
		return ictcDnaCode;
	}

	public void setIctcDnaCode(String ictcDnaCode) {
		this.ictcDnaCode = ictcDnaCode;
	}

	public String getInfantDnaCode() {
		return infantDnaCode;
	}

	public void setInfantDnaCode(String infantDnaCode) {
		this.infantDnaCode = infantDnaCode;
	}

	public String getInfantPID() {
		return infantPID;
	}

	public void setInfantPID(String infantPID) {
		this.infantPID = infantPID;
	}

	public Long getMotherId() {
		return motherId;
	}

	public void setMotherId(Long motherId) {
		this.motherId = motherId;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getMotherUid() {
		return motherUid;
	}

	public void setMotherUid(String motherUid) {
		this.motherUid = motherUid;
	}

	public String getMotherContact() {
		return motherContact;
	}

	public void setMotherContact(String motherContact) {
		this.motherContact = motherContact;
	}

	public String getMotherAddress() {
		return motherAddress;
	}

	public void setMotherAddress(String motherAddress) {
		this.motherAddress = motherAddress;
	}

	public String getMotherArtNumber() {
		return motherArtNumber;
	}

	public void setMotherArtNumber(String motherArtNumber) {
		this.motherArtNumber = motherArtNumber;
	}

	public String getMotherPreArtNumber() {
		return motherPreArtNumber;
	}

	public void setMotherPreArtNumber(String motherPreArtNumber) {
		this.motherPreArtNumber = motherPreArtNumber;
	}

	public Boolean getIsPreviousTestDone() {
		return isPreviousTestDone;
	}

	public void setIsPreviousTestDone(Boolean isPreviousTestDone) {
		this.isPreviousTestDone = isPreviousTestDone;
	}

	public String getPreviousTestResult() {
		return previousTestResult;
	}

	public void setPreviousTestResult(String previousTestResult) {
		this.previousTestResult = previousTestResult;
	}

	public String getLabSpecimenId() {
		return labSpecimenId;
	}

	public void setLabSpecimenId(String labSpecimenId) {
		this.labSpecimenId = labSpecimenId;
	}

	public String getFeedingType() {
		return feedingType;
	}

	public void setFeedingType(String feedingType) {
		this.feedingType = feedingType;
	}

}
