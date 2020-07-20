package gov.naco.soch.lab.dto;

import java.util.List;

public class InfantTestHistoryDto {

	private Long beneficiaryId;
	private String beneficiaryUid;
	private String beneficiaryName;
	private Long motherId;
	private String motherName;
	private String motherUid;
	private List<TestHistoryDto> testHistoryDetails;

	public InfantTestHistoryDto() {

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

	public List<TestHistoryDto> getTestHistoryDetails() {
		return testHistoryDetails;
	}

	public void setTestHistoryDetails(List<TestHistoryDto> testHistoryDetails) {
		this.testHistoryDetails = testHistoryDetails;
	}

}
