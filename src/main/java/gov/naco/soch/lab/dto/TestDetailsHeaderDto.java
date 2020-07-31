package gov.naco.soch.lab.dto;

import java.util.Date;

public class TestDetailsHeaderDto {
	
	private Date artStartDate;
	private TestDetailsBodyDto currentVlCount;
	private String changePercent;
	
	public Date getArtStartDate() {
		return artStartDate;
	}
	public void setArtStartDate(Date artStartDate) {
		this.artStartDate = artStartDate;
	}
	public TestDetailsBodyDto getCurrentVlCount() {
		return currentVlCount;
	}
	public void setCurrentVlCount(TestDetailsBodyDto currentVlCount) {
		this.currentVlCount = currentVlCount;
	}
	public String getChangePercent() {
		return changePercent;
	}
	public void setChangePercent(String changePercent) {
		this.changePercent = changePercent;
	}
	
	
	
	

}
