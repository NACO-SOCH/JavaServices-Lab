package gov.naco.soch.lab.dto;

import java.time.LocalDate;

import gov.naco.soch.dto.BaseDto;

public class ResultDto extends BaseDto {

	private static final long serialVersionUID = 1L;

	private String barcode;
	private String art_num;
	private String pre_art_num;
	private String patient_name;
	private String gender;
	private LocalDate birth_date;
	private LocalDate collection_date;
	private LocalDate dispatch_date;
	private String test_type;
	private String sample_status;
	private String age;
	private String client_code;

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}

	public String getArt_num() {
		return art_num;
	}

	public void setArt_num(String art_num) {
		this.art_num = art_num;
	}

	public String getPre_art_num() {
		return pre_art_num;
	}

	public void setPre_art_num(String pre_art_num) {
		this.pre_art_num = pre_art_num;
	}

	public String getPatient_name() {
		return patient_name;
	}

	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(LocalDate birth_date) {
		this.birth_date = birth_date;
	}

	public LocalDate getCollection_date() {
		return collection_date;
	}

	public void setCollection_date(LocalDate collection_date) {
		this.collection_date = collection_date;
	}

	public LocalDate getDispatch_date() {
		return dispatch_date;
	}

	public void setDispatch_date(LocalDate dispatch_date) {
		this.dispatch_date = dispatch_date;
	}

	public String getTest_type() {
		return test_type;
	}

	public void setTest_type(String test_type) {
		this.test_type = test_type;
	}

	public String getSample_status() {
		return sample_status;
	}

	public void setSample_status(String sample_status) {
		this.sample_status = sample_status;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getClient_code() {
		return client_code;
	}

	public void setClient_code(String client_code) {
		this.client_code = client_code;
	}

}
