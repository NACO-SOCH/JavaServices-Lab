package gov.naco.soch.lab.dto;

import java.time.LocalDateTime;

public class LabTestSampleDto {

	private Long id;
	private Long testBatchId;
	private Long beneficiaryId;
	private String beneficiaryName;
	private Long artId;
	private String artNo;
	private String barcodeNumber;
	private Long testTypeId;
	private Long sampleStatusId;
	private Long remarksId;
	private Long resultStatusId;
	private LocalDateTime sampleCollectedDate;
	private LocalDateTime sampleDispatchDate;
	private LocalDateTime sampleReceviedDate;
	private LocalDateTime resultRecivedDate;
	private LocalDateTime resultApprovedDate;
	private LocalDateTime resultDispatchDate;
	private Long resultTypeId;
	private String resultValue;
	private String logValue;
	private Boolean isError;
	private Long errorCode;
	private Long testMachineTypeId;
	private Long testMachineId;
	private Long authorizerId;
	private String authorizerSignature;
	private String typeOfSpecimen;
	private Long labTechnicianId;
	private String labTechnicianSignature;
	private Long labInchargeId;
	private String labInchargeSignature;
	private String testRequestFormLink;

	public LabTestSampleDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getTestBatchId() {
		return testBatchId;
	}

	public void setTestBatchId(Long testBatchId) {
		this.testBatchId = testBatchId;
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

	public Long getSampleStatusId() {
		return sampleStatusId;
	}

	public void setSampleStatusId(Long sampleStatusId) {
		this.sampleStatusId = sampleStatusId;
	}

	public Long getRemarksId() {
		return remarksId;
	}

	public void setRemarksId(Long remarksId) {
		this.remarksId = remarksId;
	}

	public Long getResultStatusId() {
		return resultStatusId;
	}

	public void setResultStatusId(Long resultStatusId) {
		this.resultStatusId = resultStatusId;
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

	public LocalDateTime getSampleReceviedDate() {
		return sampleReceviedDate;
	}

	public void setSampleReceviedDate(LocalDateTime sampleReceviedDate) {
		this.sampleReceviedDate = sampleReceviedDate;
	}

	public LocalDateTime getResultRecivedDate() {
		return resultRecivedDate;
	}

	public void setResultRecivedDate(LocalDateTime resultRecivedDate) {
		this.resultRecivedDate = resultRecivedDate;
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

}
