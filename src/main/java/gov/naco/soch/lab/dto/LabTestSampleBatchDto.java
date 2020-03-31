package gov.naco.soch.lab.dto;

import java.time.LocalDateTime;
import java.util.List;

public class LabTestSampleBatchDto {

	private Long id;
	private String bdnSerialNumber;
	private Long artcId;
	private String artcName;
	private String artcAddress;
	private Long artcLabTechId;
	private String artcLabTechName;
	private String artcLabTechContact;
	private Long labId;
	private String labName;
	private String labAddress;
	private Long labTechId;
	private String labTechName;
	private String labTechContact;
	private LocalDateTime dispatchDate;
	private LocalDateTime receivedDate;
	private Long num_ofSamples;
	private Long acceptedSamples;
	private Long rejectedSamples;
	private Long batchStatusId;
	private String batchStatus;
	private List<LabTestSampleDto> labTestSampleDtoList;

	public LabTestSampleBatchDto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getLabTechId() {
		return labTechId;
	}

	public void setLabTechId(Long labTechId) {
		this.labTechId = labTechId;
	}

	public String getLabTechName() {
		return labTechName;
	}

	public void setLabTechName(String labTechName) {
		this.labTechName = labTechName;
	}

	public String getLabTechContact() {
		return labTechContact;
	}

	public void setLabTechContact(String labTechContact) {
		this.labTechContact = labTechContact;
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

	public Long getBatchStatusId() {
		return batchStatusId;
	}

	public void setBatchStatusId(Long batchStatusId) {
		this.batchStatusId = batchStatusId;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public List<LabTestSampleDto> getLabTestSampleDtoList() {
		return labTestSampleDtoList;
	}

	public void setLabTestSampleDtoList(List<LabTestSampleDto> labTestSampleDtoList) {
		this.labTestSampleDtoList = labTestSampleDtoList;
	}

}
