package gov.naco.soch.lab.dto;

import java.util.Date;

public class TestDetailsAdheranceDto {

	private String adherancePercentage;
	private Date dispensationDate;

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
