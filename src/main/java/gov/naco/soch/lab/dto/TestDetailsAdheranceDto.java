package gov.naco.soch.lab.dto;

import java.util.Date;

public class TestDetailsAdheranceDto {

	private Integer resultId;
	private String adherancePercentage;
	private Date dispensationDate;

	
	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public String getAdherancePercentage() {
		return adherancePercentage;
	}

	public void setAdherancePercentage(String adherancePercentage) {
		this.adherancePercentage = adherancePercentage;
	}

	public Date getDispensationDate() {
		return dispensationDate;
	}

	public void setDispensationDate(Date dispensationDate) {
		this.dispensationDate = dispensationDate;
	}

	

	
}
