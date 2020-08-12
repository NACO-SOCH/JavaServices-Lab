package gov.naco.soch.lab.dto;

import java.util.List;

public class TestSamplesResponseDto {

	private Long totalCount;
	private Integer currentCount;
	private Integer pageNumber;
	private List<TestResultDto> samples;

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getCurrentCount() {
		return currentCount;
	}

	public void setCurrentCount(Integer currentCount) {
		this.currentCount = currentCount;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public List<TestResultDto> getSamples() {
		return samples;
	}

	public void setSamples(List<TestResultDto> samples) {
		this.samples = samples;
	}

}
