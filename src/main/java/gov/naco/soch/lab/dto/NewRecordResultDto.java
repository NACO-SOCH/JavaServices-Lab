package gov.naco.soch.lab.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class NewRecordResultDto {
		private Long batchId;
	    private Long id;
	    private String createdBy;
	    private LocalDateTime createdTime;
	    private String modifiedBy;
	    private LocalDateTime modifiedTime;
	    private boolean isUndone;
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
	    private LocalDate dateOfBirth;
	    private int age;
	    private String gender;
	    private String beneficiaryStatus;
	    private String barcodeNumber;
	    private boolean isDelete;
	    private boolean isError;
	    private String artNumber;
	    private LocalDateTime sampleDispatchDate;
	    private LocalDateTime sampleCollectedDate;
	    private LocalDateTime sampleReceivedDate;
	    private Long labTechnicianId;
	    private Long sampleCollectedFacilityId;
	    private Long labInchargeId;
	    private String labInchargeSignature;
	    private String labTechnicianSignature;
	    private Long sampleStatusId;
	    private String sampleStatus;
	    private Long testTypeId;
	    private Long testId;
	    private String testType;
	    private Long testBatchId;
	    private Long remarksId;
	    private LocalDate nextAppointmentDate;
	    private LocalDateTime resultApprovedDate;
	    private LocalDateTime resultDispatchDate;
	    private Long resultStatusId;
	    private String resultStatus;
	    private LocalDateTime resultReceivedDate;
	    private String resultValue;
	    private LocalDateTime labSpecimenId;
	    private Long vlTestCount;
	    private Long dispatchedToLabId;
	    private String labName;
	    private String labAddress;
	    private String labCode;
	    private String labInchargeName;
	    private String contact;
	    private String populationType;
	    private Long artcLabTechId;
	    private String artLabTechName;
	    private String artcLabTechContact;
	    private LocalDate dispatchDate;
	    private LocalDate receivedDate;
	    private Long numOfSamples;
	    private Long acceptedSamples;
	    private Long rejectedSamples;
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
		public boolean isUndone() {
			return isUndone;
		}
		public void setUndone(boolean isUndone) {
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
		public LocalDate getDateOfBirth() {
			return dateOfBirth;
		}
		public void setDateOfBirth(LocalDate dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
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
		public boolean isDelete() {
			return isDelete;
		}
		public void setDelete(boolean isDelete) {
			this.isDelete = isDelete;
		}
		public boolean isError() {
			return isError;
		}
		public void setError(boolean isError) {
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
		public Long getTestId() {
			return testId;
		}
		public void setTestId(Long testId) {
			this.testId = testId;
		}
		public String getTestType() {
			return testType;
		}
		public void setTestType(String testType) {
			this.testType = testType;
		}
		public Long getTestBatchId() {
			return testBatchId;
		}
		public void setTestBatchId(Long testBatchId) {
			this.testBatchId = testBatchId;
		}
		public Long getRemarksId() {
			return remarksId;
		}
		public void setRemarksId(Long remarksId) {
			this.remarksId = remarksId;
		}
		public LocalDate getNextAppointmentDate() {
			return nextAppointmentDate;
		}
		public void setNextAppointmentDate(LocalDate nextAppointmentDate) {
			this.nextAppointmentDate = nextAppointmentDate;
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
		public String getResultValue() {
			return resultValue;
		}
		public void setResultValue(String resultValue) {
			this.resultValue = resultValue;
		}
		public LocalDateTime getLabSpecimenId() {
			return labSpecimenId;
		}
		public void setLabSpecimenId(LocalDateTime labSpecimenId) {
			this.labSpecimenId = labSpecimenId;
		}
		public Long getVlTestCount() {
			return vlTestCount;
		}
		public void setVlTestCount(Long vlTestCount) {
			this.vlTestCount = vlTestCount;
		}
		public Long getDispatchedToLabId() {
			return dispatchedToLabId;
		}
		public void setDispatchedToLabId(Long dispatchedToLabId) {
			this.dispatchedToLabId = dispatchedToLabId;
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
		public String getLabInchargeName() {
			return labInchargeName;
		}
		public void setLabInchargeName(String labInchargeName) {
			this.labInchargeName = labInchargeName;
		}
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
		public LocalDate getDispatchDate() {
			return dispatchDate;
		}
		public void setDispatchDate(LocalDate dispatchDate) {
			this.dispatchDate = dispatchDate;
		}
		public LocalDate getReceivedDate() {
			return receivedDate;
		}
		public void setReceivedDate(LocalDate receivedDate) {
			this.receivedDate = receivedDate;
		}
		public Long getNumOfSamples() {
			return numOfSamples;
		}
		public void setNumOfSamples(Long numOfSamples) {
			this.numOfSamples = numOfSamples;
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
		public NewRecordResultDto(Long batchId, Long id, String createdBy, LocalDateTime createdTime, String modifiedBy,
				LocalDateTime modifiedTime, boolean isUndone, String artcSampleStatus, Long authorizerId,
				String authorizerSignature, String bdnSerialNumber, Long artcId, String artcName, String artcAddress,
				String artcCode, String artCode, String uid, Long beneficiaryId, String beneficiaryName,
				LocalDate dateOfBirth, int age, String gender, String beneficiaryStatus, String barcodeNumber,
				boolean isDelete, boolean isError, String artNumber, LocalDateTime sampleDispatchDate,
				LocalDateTime sampleCollectedDate, LocalDateTime sampleReceivedDate, Long labTechnicianId,
				Long sampleCollectedFacilityId, Long labInchargeId, String labInchargeSignature,
				String labTechnicianSignature, Long sampleStatusId, String sampleStatus, Long testTypeId, Long testId,
				String testType, Long testBatchId, Long remarksId, LocalDate nextAppointmentDate,
				LocalDateTime resultApprovedDate, LocalDateTime resultDispatchDate, Long resultStatusId,
				String resultStatus, LocalDateTime resultReceivedDate, String resultValue, LocalDateTime labSpecimenId,
				Long vlTestCount, Long dispatchedToLabId, String labName, String labAddress, String labCode,
				String labInchargeName, String contact, String populationType, Long artcLabTechId,
				String artLabTechName, String artcLabTechContact, LocalDate dispatchDate, LocalDate receivedDate,
				Long numOfSamples, Long acceptedSamples, Long rejectedSamples) {
			super();
			this.batchId = batchId;
			this.id = id;
			this.createdBy = createdBy;
			this.createdTime = createdTime;
			this.modifiedBy = modifiedBy;
			this.modifiedTime = modifiedTime;
			this.isUndone = isUndone;
			this.artcSampleStatus = artcSampleStatus;
			this.authorizerId = authorizerId;
			this.authorizerSignature = authorizerSignature;
			this.bdnSerialNumber = bdnSerialNumber;
			this.artcId = artcId;
			this.artcName = artcName;
			this.artcAddress = artcAddress;
			this.artcCode = artcCode;
			this.artCode = artCode;
			this.uid = uid;
			this.beneficiaryId = beneficiaryId;
			this.beneficiaryName = beneficiaryName;
			this.dateOfBirth = dateOfBirth;
			this.age = age;
			this.gender = gender;
			this.beneficiaryStatus = beneficiaryStatus;
			this.barcodeNumber = barcodeNumber;
			this.isDelete = isDelete;
			this.isError = isError;
			this.artNumber = artNumber;
			this.sampleDispatchDate = sampleDispatchDate;
			this.sampleCollectedDate = sampleCollectedDate;
			this.sampleReceivedDate = sampleReceivedDate;
			this.labTechnicianId = labTechnicianId;
			this.sampleCollectedFacilityId = sampleCollectedFacilityId;
			this.labInchargeId = labInchargeId;
			this.labInchargeSignature = labInchargeSignature;
			this.labTechnicianSignature = labTechnicianSignature;
			this.sampleStatusId = sampleStatusId;
			this.sampleStatus = sampleStatus;
			this.testTypeId = testTypeId;
			this.testId = testId;
			this.testType = testType;
			this.testBatchId = testBatchId;
			this.remarksId = remarksId;
			this.nextAppointmentDate = nextAppointmentDate;
			this.resultApprovedDate = resultApprovedDate;
			this.resultDispatchDate = resultDispatchDate;
			this.resultStatusId = resultStatusId;
			this.resultStatus = resultStatus;
			this.resultReceivedDate = resultReceivedDate;
			this.resultValue = resultValue;
			this.labSpecimenId = labSpecimenId;
			this.vlTestCount = vlTestCount;
			this.dispatchedToLabId = dispatchedToLabId;
			this.labName = labName;
			this.labAddress = labAddress;
			this.labCode = labCode;
			this.labInchargeName = labInchargeName;
			this.contact = contact;
			this.populationType = populationType;
			this.artcLabTechId = artcLabTechId;
			this.artLabTechName = artLabTechName;
			this.artcLabTechContact = artcLabTechContact;
			this.dispatchDate = dispatchDate;
			this.receivedDate = receivedDate;
			this.numOfSamples = numOfSamples;
			this.acceptedSamples = acceptedSamples;
			this.rejectedSamples = rejectedSamples;
		}
	    
	    
	    
	    
}
