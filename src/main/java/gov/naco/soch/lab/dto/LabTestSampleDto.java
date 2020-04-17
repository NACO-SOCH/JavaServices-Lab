package gov.naco.soch.lab.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class LabTestSampleDto {

	private Long sampleId;
	private Long batchId;
	private Long beneficiaryId;
	private String beneficiaryName;
	private String beneficiaryUid;
	private LocalDate beneficiaryDob;
	private String beneficiaryAge;
	private String beneficiaryGender;
	private String beneficiaryHivStatus;
	private String artNumber;
	private String preArtNumber;
	private Long artId;
	private String artNo;
	private String barcodeNumber;
	private Long testTypeId;
	private String testType;
	private Long sampleStatusId;
	private String sampleStatus;
	private Long remarksId;
	private String remarks;
	private Long resultStatusId;
	private String resultStatus;
	private LocalDateTime sampleCollectedDate;
	private LocalDateTime sampleDispatchDate;
	private LocalDateTime sampleReceivedDate;
	private LocalDateTime resultReceivedDate;
	private LocalDateTime resultApprovedDate;
	private LocalDateTime resultDispatchDate;
	private Long resultTypeId;
	private String resultType;
	private String resultValue;
	private String logValue;
	private Boolean isError;
	private Long errorCode;
	private Long testMachineTypeId;
	private Long testMachineId;
	private String testMachine;
	private Long authorizerId;
	private String authorizerName;
	private String authorizerSignature;
	private String typeOfSpecimen;
	private Long labTechnicianId;
	private String labTechnicianName;
	private String labTechnicianContact;
	private String labTechnicianSignature;
	private Long labInchargeId;
	private String labInchargeName;
	private String labInChargeContact;
	private String labInchargeSignature;
	private String testRequestFormLink;

	private String populationType;
	private String department;

	private String ictcDnaCode;
	private String infantDnaCode;

	private Long motherId;
	private String motherName;
	private String motherUid;
	private String motherContact;
	private String motherAddress;

	private Boolean isPreviousTestDone;
	private String previousTestResult;
	private String labSpecimenId;
	private String feedingType;

	public LabTestSampleDto() {
		super();
	}

	public Long getSampleId() {
		return sampleId;
	}

	public void setSampleId(Long sampleId) {
		this.sampleId = sampleId;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}

	public Long getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(Long beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryUid() {
		return beneficiaryUid;
	}

	public void setBeneficiaryUid(String beneficiaryUid) {
		this.beneficiaryUid = beneficiaryUid;
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

	public Long getArtId() {
		return artId;
	}

	public void setArtId(Long artId) {
		this.artId = artId;
	}

	public String getArtNo() {
		return artNo;
	}

	public void setArtNo(String artNo) {
		this.artNo = artNo;
	}

	public String getBarcodeNumber() {
		return barcodeNumber;
	}

	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
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

	public Long getRemarksId() {
		return remarksId;
	}

	public void setRemarksId(Long remarksId) {
		this.remarksId = remarksId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public LocalDateTime getSampleCollectedDate() {
		return sampleCollectedDate;
	}

	public void setSampleCollectedDate(LocalDateTime sampleCollectedDate) {
		this.sampleCollectedDate = sampleCollectedDate;
	}

	public LocalDateTime getSampleDispatchDate() {
		return sampleDispatchDate;
	}

	public void setSampleDispatchDate(LocalDateTime sampleDispatchDate) {
		this.sampleDispatchDate = sampleDispatchDate;
	}

	public LocalDateTime getSampleReceivedDate() {
		return sampleReceivedDate;
	}

	public void setSampleReceivedDate(LocalDateTime sampleReceivedDate) {
		this.sampleReceivedDate = sampleReceivedDate;
	}

	public LocalDateTime getResultReceivedDate() {
		return resultReceivedDate;
	}

	public void setResultReceivedDate(LocalDateTime resultReceivedDate) {
		this.resultReceivedDate = resultReceivedDate;
	}

	public LocalDateTime getResultApprovedDate() {
		return resultApprovedDate;
	}

	public void setResultApprovedDate(LocalDateTime resultApprovedDate) {
		this.resultApprovedDate = resultApprovedDate;
	}

	public LocalDateTime getResultDispatchDate() {
		return resultDispatchDate;
	}

	public void setResultDispatchDate(LocalDateTime resultDispatchDate) {
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

	public Long getTestMachineTypeId() {
		return testMachineTypeId;
	}

	public void setTestMachineTypeId(Long testMachineTypeId) {
		this.testMachineTypeId = testMachineTypeId;
	}

	public Long getTestMachineId() {
		return testMachineId;
	}

	public void setTestMachineId(Long testMachineId) {
		this.testMachineId = testMachineId;
	}

	public String getTestMachine() {
		return testMachine;
	}

	public void setTestMachine(String testMachine) {
		this.testMachine = testMachine;
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

	public String getTypeOfSpecimen() {
		return typeOfSpecimen;
	}

	public void setTypeOfSpecimen(String typeOfSpecimen) {
		this.typeOfSpecimen = typeOfSpecimen;
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

	public String getLabTechnicianSignature() {
		return labTechnicianSignature;
	}

	public void setLabTechnicianSignature(String labTechnicianSignature) {
		this.labTechnicianSignature = labTechnicianSignature;
	}

	public Long getLabInchargeId() {
		return labInchargeId;
	}

	public void setLabInchargeId(Long labInchargeId) {
		this.labInchargeId = labInchargeId;
	}

	public String getLabInchargeName() {
		return labInchargeName;
	}

	public void setLabInchargeName(String labInchargeName) {
		this.labInchargeName = labInchargeName;
	}

	public String getLabInChargeContact() {
		return labInChargeContact;
	}

	public void setLabInChargeContact(String labInChargeContact) {
		this.labInChargeContact = labInChargeContact;
	}

	public String getLabInchargeSignature() {
		return labInchargeSignature;
	}

	public void setLabInchargeSignature(String labInchargeSignature) {
		this.labInchargeSignature = labInchargeSignature;
	}

	public String getTestRequestFormLink() {
		return testRequestFormLink;
	}

	public void setTestRequestFormLink(String testRequestFormLink) {
		this.testRequestFormLink = testRequestFormLink;
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
