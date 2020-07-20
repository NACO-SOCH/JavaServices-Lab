package gov.naco.soch.lab.dto;

import java.util.List;

public class BeneficiaryTestDetailsDto {
	
	private List<AdherenceDetailsDto> adherenceDetails;
	private List<CDFourTestCountDetailsDto> cdfourTestCountDetails;
	private List<VLTestCountDetailsDto> vlTestCountDetails;
	private TestDetailsHeaderDto testDetailsHeaderDto;
	
	public List<AdherenceDetailsDto> getAdherenceDetails() {
		return adherenceDetails;
	}
	public void setAdherenceDetails(List<AdherenceDetailsDto> adherenceDetails) {
		this.adherenceDetails = adherenceDetails;
	}
	public List<CDFourTestCountDetailsDto> getCdfourTestCountDetails() {
		return cdfourTestCountDetails;
	}
	public void setCdfourTestCountDetails(List<CDFourTestCountDetailsDto> cdfourTestCountDetails) {
		this.cdfourTestCountDetails = cdfourTestCountDetails;
	}
	public List<VLTestCountDetailsDto> getVlTestCountDetails() {
		return vlTestCountDetails;
	}
	public void setVlTestCountDetails(List<VLTestCountDetailsDto> vlTestCountDetails) {
		this.vlTestCountDetails = vlTestCountDetails;
	}
	public TestDetailsHeaderDto getTestDetailsHeaderDto() {
		return testDetailsHeaderDto;
	}
	public void setTestDetailsHeaderDto(TestDetailsHeaderDto testDetailsHeaderDto) {
		this.testDetailsHeaderDto = testDetailsHeaderDto;
	}

}
