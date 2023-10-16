package gov.naco.soch.lab.dto;

import java.util.List;

public class TestSamplesResponseNewDTO {

	private Long totalCount;
	private Integer currentCount;
	private Integer pageNumber;
	private List<TestRecordResultDto> samples;

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

	public List<TestRecordResultDto> getSamples() {
		return samples;
	}

	public void setSamples(List<TestRecordResultDto> samples) {
		this.samples = samples;
	}

}
