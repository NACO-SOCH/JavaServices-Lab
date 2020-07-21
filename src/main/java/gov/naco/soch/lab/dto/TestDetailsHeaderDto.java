package gov.naco.soch.lab.dto;

import java.util.Date;

public class TestDetailsHeaderDto {
	
	private Date artStartDate;
	private int currentVlCount;
	private int previousVlCount;
	
	public Date getArtStartDate() {
		return artStartDate;
	}
	public void setArtStartDate(Date artStartDate) {
		this.artStartDate = artStartDate;
	}
	public int getCurrentVlCount() {
		return currentVlCount;
	}
	public void setCurrentVlCount(int currentVlCount) {
		this.currentVlCount = currentVlCount;
	}
	public int getPreviousVlCount() {
		return previousVlCount;
	}
	public void setPreviousVlCount(int previousVlCount) {
		this.previousVlCount = previousVlCount;
	}
	
	
	
	

}
