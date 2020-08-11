package gov.naco.soch.lab.dto;

import java.util.List;

public class ReceiceSamplesResponseDto {

	private Long totalCount;
	private Integer currentCount;
	private Integer pageNumber;
	private List<LabTestSampleBatchDto> batches;

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

	public List<LabTestSampleBatchDto> getBatches() {
		return batches;
	}

	public void setBatches(List<LabTestSampleBatchDto> batches) {
		this.batches = batches;
	}

}
