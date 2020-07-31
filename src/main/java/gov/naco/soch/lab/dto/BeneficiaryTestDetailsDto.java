package gov.naco.soch.lab.dto;

import java.util.List;

public class BeneficiaryTestDetailsDto {
	
	private TestDetailsHeaderDto testDetailsHeaderDto;
	private List<TestDetailsBodyDto> cdfourTestCountDetails;
	private List<TestDetailsBodyDto> vlTestCountDetails;
	private List<TestDetailsAdheranceDto> adherenceDetails;
	
	public List<TestDetailsAdheranceDto> getAdherenceDetails() {
		return adherenceDetails;
	}
	public void setAdherenceDetails(List<TestDetailsAdheranceDto> adherenceDetails) {
		this.adherenceDetails = adherenceDetails;
	}
	public List<TestDetailsBodyDto> getCdfourTestCountDetails() {
		return cdfourTestCountDetails;
	}
	public void setCdfourTestCountDetails(List<TestDetailsBodyDto> cdfourTestCountDetails) {
		this.cdfourTestCountDetails = cdfourTestCountDetails;
	}
	public List<TestDetailsBodyDto> getVlTestCountDetails() {
		return vlTestCountDetails;
	}
	public void setVlTestCountDetails(List<TestDetailsBodyDto> vlTestCountDetails) {
		this.vlTestCountDetails = vlTestCountDetails;
	}
	public TestDetailsHeaderDto getTestDetailsHeaderDto() {
		return testDetailsHeaderDto;
	}
	public void setTestDetailsHeaderDto(TestDetailsHeaderDto testDetailsHeaderDto) {
		this.testDetailsHeaderDto = testDetailsHeaderDto;
	}
}
