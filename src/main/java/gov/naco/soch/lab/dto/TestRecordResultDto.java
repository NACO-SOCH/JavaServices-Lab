package gov.naco.soch.lab.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TestRecordResultDto {
	
	private Long batchId;
    private Long id;
    private String createdBy;
    private LocalDateTime createdTime; 
    private LocalDateTime modifiedTime;
    private String modifiedBy;
    private Boolean isUndone;
    private String artcSampleStatus;
    private Long authorizerId;
    private String authorizerSignature;
    private String bdnSerialNumber;
    private Long artcId;
    private String artcName;
    private String artcAddress;
    private String artcCode;
    private String artCode;
    private String uid;
    private Long beneficiaryId;
    private String beneficiaryName;
   // private LocalDate dateOfBirth;
//    private Date age;
	private String gender;
    private String beneficiaryStatus;
    private String barcodeNumber;
    private Boolean isDelete;
    private Boolean isError;
    private String artNumber;
    private LocalDateTime sampleDispatchDate;
    private LocalDateTime sampleCollectedDate;
    private LocalDateTime sampleReceivedDate;
    private Long labTechnicianId;
    private Long sampleCollectedFacilityId;
    private Long testId;
    private Long labInchargeId;
    private String labInchargeSignature;
    private String labTechnicianSignature;
    private Long sampleStatusId;
    private String sampleStatus;
    private Long testTypeId;
    private String testType;
    private String resultStatus;
    private LocalDateTime resultReceivedDate;
    private String resultValue;
    private Long resultTypeId;
    private Long resultStatusId;
    private Long labId;
    private String labName;
    private String labAddress;
    private String labCode;
//    private String labInchargeName;
    private String contact;
    private String populationType;
    private Long artcLabTechId;
    private String artLabTechName;
    private String artcLabTechContact;
    private LocalDateTime dispatchDate;
    private LocalDateTime receivedDate;
    private Integer numOfSamples;
    private Integer acceptedSamples;
    private Integer rejectedSamples;
//    private String labSpecimenID;
//    private LocalDate resultApprovedDate;
//    private Integer VLTestCount;
//    private Long dispatchedToLabId;
    private String artcLabTechName;
    
	public void setArtcLabTechName(String artcLabTechName) {
		this.artcLabTechName = artcLabTechName;
	}

	public String getArtcLabTechName() {
		return artcLabTechName;
	}
	
	public Long getBatchId() {
		return batchId;
	}
	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public LocalDateTime getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(LocalDateTime modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	public Boolean getIsUndone() {
		return isUndone;
	}
	public void setIsUndone(Boolean isUndone) {
		this.isUndone = isUndone;
	}
	public String getArtcSampleStatus() {
		return artcSampleStatus;
	}
	public void setArtcSampleStatus(String artcSampleStatus) {
		this.artcSampleStatus = artcSampleStatus;
	}
	public Long getAuthorizerId() {
		return authorizerId;
	}
	public void setAuthorizerId(Long authorizerId) {
		this.authorizerId = authorizerId;
	}
	public String getAuthorizerSignature() {
		return authorizerSignature;
	}
	public void setAuthorizerSignature(String authorizerSignature) {
		this.authorizerSignature = authorizerSignature;
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
	public String getArtCode() {
		return artCode;
	}
	public void setArtCode(String artCode) {
		this.artCode = artCode;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
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
//	public LocalDate getDateOfBirth() {
//		return dateOfBirth;
//	}
//	public void setDateOfBirth(LocalDate dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getBeneficiaryStatus() {
		return beneficiaryStatus;
	}
	public void setBeneficiaryStatus(String beneficiaryStatus) {
		this.beneficiaryStatus = beneficiaryStatus;
	}
	public String getBarcodeNumber() {
		return barcodeNumber;
	}
	public void setBarcodeNumber(String barcodeNumber) {
		this.barcodeNumber = barcodeNumber;
	}
	public Boolean getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}
	public Boolean getIsError() {
		return isError;
	}
	public void setIsError(Boolean isError) {
		this.isError = isError;
	}
	public String getArtNumber() {
		return artNumber;
	}
	public void setArtNumber(String artNumber) {
		this.artNumber = artNumber;
	}
	public LocalDateTime getSampleDispatchDate() {
		return sampleDispatchDate;
	}
	public void setSampleDispatchDate(LocalDateTime sampleDispatchDate) {
		this.sampleDispatchDate = sampleDispatchDate;
	}
	public LocalDateTime getSampleCollectedDate() {
		return sampleCollectedDate;
	}
	public void setSampleCollectedDate(LocalDateTime sampleCollectedDate) {
		this.sampleCollectedDate = sampleCollectedDate;
	}
//    public Date getAge() {
//		return age;
//	}
//	public void setAge(Date age) {
//		this.age = age;
//	}
	public LocalDateTime getSampleReceivedDate() {
		return sampleReceivedDate;
	}
	public void setSampleReceivedDate(LocalDateTime sampleReceivedDate) {
		this.sampleReceivedDate = sampleReceivedDate;
	}
	public Long getLabTechnicianId() {
		return labTechnicianId;
	}
	public void setLabTechnicianId(Long labTechnicianId) {
		this.labTechnicianId = labTechnicianId;
	}
	public Long getSampleCollectedFacilityId() {
		return sampleCollectedFacilityId;
	}
	public void setSampleCollectedFacilityId(Long sampleCollectedFacilityId) {
		this.sampleCollectedFacilityId = sampleCollectedFacilityId;
	}
	public Long getTestId() {
		return testId;
	}
	public void setTestId(Long testId) {
		this.testId = testId;
	}
	public Long getLabInchargeId() {
		return labInchargeId;
	}
	public void setLabInchargeId(Long labInchargeId) {
		this.labInchargeId = labInchargeId;
	}
	public String getLabInchargeSignature() {
		return labInchargeSignature;
	}
	public void setLabInchargeSignature(String labInchargeSignature) {
		this.labInchargeSignature = labInchargeSignature;
	}
	public String getLabTechnicianSignature() {
		return labTechnicianSignature;
	}
	public void setLabTechnicianSignature(String labTechnicianSignature) {
		this.labTechnicianSignature = labTechnicianSignature;
	}
	public Long getSampleStatusId() {
		return sampleStatusId;
	}
	public void setSampleStatusId(Long integer) {
		this.sampleStatusId = integer;
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
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
	public Long getResultTypeId() {
		return resultTypeId;
	}
	public void setResultTypeId(Long resultTypeId) {
		this.resultTypeId = resultTypeId;
	}
	public Long getResultStatusId() {
		return resultStatusId;
	}
	public void setResultStatusId(Long resultStatusId) {
		this.resultStatusId = resultStatusId;
	}
//	public Long getLabId() {
//		return labId;
//	}
//	public void setLabId(Long labId) {
//		this.labId = labId;
//	}
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
//	public String getLabInchargeName() {
//		return labInchargeName;
//	}
//	public void setLabInchargeName(String labInchargeName) {
//		this.labInchargeName = labInchargeName;
//	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPopulationType() {
		return populationType;
	}
	public void setPopulationType(String populationType) {
		this.populationType = populationType;
	}
	public Long getArtcLabTechId() {
		return artcLabTechId;
	}
	public void setArtcLabTechId(Long artcLabTechId) {
		this.artcLabTechId = artcLabTechId;
	}
	public String getArtLabTechName() {
		return artLabTechName;
	}
	public void setArtLabTechName(String artLabTechName) {
		this.artLabTechName = artLabTechName;
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
	public Integer getNumOfSamples() {
		return numOfSamples;
	}
	public void setNumOfSamples(Integer numOfSamples) {
		this.numOfSamples = numOfSamples;
	}
	public Integer getAcceptedSamples() {
		return acceptedSamples;
	}
	public void setAcceptedSamples(Integer acceptedSamples) {
		this.acceptedSamples = acceptedSamples;
	}
	public Integer getRejectedSamples() {
		return rejectedSamples;
	}
	public void setRejectedSamples(Integer rejectedSamples) {
		this.rejectedSamples = rejectedSamples;
	}
//	public String getLabSpecimenID() {
//		return labSpecimenID;
//	}
//	public void setLabSpecimenID(String labSpecimenID) {
//		this.labSpecimenID = labSpecimenID;
//	}
//	public LocalDate getResultApprovedDate() {
//		return resultApprovedDate;
//	}
//	public void setResultApprovedDate(LocalDate resultApprovedDate) {
//		this.resultApprovedDate = resultApprovedDate;
//	}
//	public Integer getVLTestCount() {
//		return VLTestCount;
//	}
//	public void setVLTestCount(Integer vLTestCount) {
//		VLTestCount = vLTestCount;
//	}
//	public Long getDispatchedToLabId() {
//		return dispatchedToLabId;
//	}
//	public void setDispatchedToLabId(Long dispatchedToLabId) {
//		this.dispatchedToLabId = dispatchedToLabId;
//	}
	public Long getLabId() {
		return labId;
	}
	public void setLabId(Long labId) {
		this.labId = labId;
	}
    
    
   

}
