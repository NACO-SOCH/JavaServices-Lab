package gov.naco.soch.lab.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdvanceSearchMapperUtil {
	
	public static List<String> queryCreaterForAdvanceSearchReceiveSampleList(Long labId, Map<String, String> searchValue) {
		
		String status = searchValue.get("status");
		String facility = searchValue.get("facility");
		String fromDate = searchValue.get("fromDate");
		String toDate = searchValue.get("toDate");

		List<String> searchQueryList = new ArrayList<>();

		String searchQuery = "select * from soch.lab_test_sample_batch as lts where ";

		if (status != null && !status.equals("")) {
			Long sampleStatus = Long.valueOf(status);
			searchQuery = searchQuery.concat(" lts.batch_status_id= " + sampleStatus + " and ");
		}
		if (facility != null && facility != "") {
			Long byFacility = Long.valueOf(facility);
			searchQuery = searchQuery.concat(" lts.artc_id= " + byFacility + " and ");
		}
		if (fromDate != null && fromDate != "" && (toDate == null || toDate == "")) {
			searchQuery = searchQuery.concat(" cast(lts.dispatch_date as date) >= '" + fromDate + "' and ");
		}
		if (toDate != null && toDate != "" && (fromDate == null || fromDate == "")) {
			searchQuery = searchQuery.concat(" cast(lts.dispatch_date as date) <= '" + toDate + "' and ");
		}
		
		if (fromDate != null && fromDate != "" && toDate != null && toDate != "") {
			searchQuery = searchQuery.concat(" cast(lts.dispatch_date as date) >= '" + fromDate + "' and cast(lts.dispatch_date as date) <= '" + toDate + "' and ");
		}
		
		searchQuery = searchQuery.concat(" lts.is_delete = false and lts.lab_id= " + labId);

		searchQueryList.add(searchQuery);

		return searchQueryList;
	}

	public static List<String> queryCreaterForAdvanceSearchRecordResultsList(Long labId,Map<String, String> searchValue) {
		
		String resultStatus = searchValue.get("resultStatus");
		String facility = searchValue.get("facility");
		String fromDate = searchValue.get("fromDate");
		String toDate = searchValue.get("toDate");

		List<String> searchQueryList = new ArrayList<>();

		String searchQuery = "select * from soch.lab_test_sample as lts where ";

		if (resultStatus != null && resultStatus != "") {
			Long status = Long.valueOf(resultStatus);
			searchQuery = searchQuery.concat(" lts.result_status_id= " + status + " and ");
		}
		if (facility != null && facility != "") {
			Long byFacility = Long.valueOf(facility);
			searchQuery = searchQuery.concat(" lts.sample_collected_facility_id= " + byFacility + " and ");
		}
		if (fromDate != null && fromDate != "" && (toDate == null || toDate == "")) {
			searchQuery = searchQuery.concat(" cast(lts.sample_collected_date as date) >= '" + fromDate + "' and ");
		}
		if (toDate != null && toDate != "" && (fromDate == null || fromDate == "")) {
			searchQuery = searchQuery.concat(" cast(lts.sample_collected_date as date) <= '" + toDate + "' and ");
		}
		
		if (fromDate != null && fromDate != "" && toDate != null && toDate != "") {
			searchQuery = searchQuery.concat(" cast(lts.sample_collected_date as date) >= '" + fromDate + "' and cast(lts.sample_collected_date as date) <= '" + toDate + "' and ");
		}
			
		searchQuery = searchQuery.concat(" lts.is_delete = false ");

		searchQueryList.add(searchQuery);

		return searchQueryList;
	}
	
	public static List<String> queryCreaterForAdvanceSearchResultsList(Long labId,Map<String, String> searchValue,Boolean isTestResult,
			Boolean isUnderApproval) {
		
		String resultStatus = searchValue.get("resultStatus");
		String facility = searchValue.get("facility");
		String fromDate = searchValue.get("fromDate");
		String toDate = searchValue.get("toDate");
		String sampleStatus = searchValue.get("sampleStatus");
		String testType = searchValue.get("testType");

		List<String> searchQueryList = new ArrayList<>();

		String searchQuery = "select * from soch.lab_test_sample as lts where ";

		if (facility != null && facility != "") {
			Long byFacility = Long.valueOf(facility);
			searchQuery = searchQuery.concat(" lts.sample_collected_facility_id= " + byFacility + " and ");
		}
		
		if (fromDate != null && fromDate != "" && (toDate == null || toDate == "")) {
			searchQuery = searchQuery.concat(" cast(lts.result_received_date as date) >= '" + fromDate + "' and ");
		}
		if (toDate != null && toDate != "" && (fromDate == null || fromDate == "")) {
			searchQuery = searchQuery.concat(" cast(lts.result_received_date as date) <= '" + toDate + "' and ");
		}
		
		if (fromDate != null && fromDate != "" && toDate != null && toDate != "") {
			searchQuery = searchQuery.concat(" cast(lts.result_received_date as date) >= '" + fromDate + "' and cast(lts.result_received_date as date) <= '" + toDate + "' and ");
		}
		
		if(isTestResult) {
			if (sampleStatus != null && sampleStatus != "") {
				Long status = Long.valueOf(sampleStatus);
				searchQuery = searchQuery.concat(" lts.sample_status_id= " + status + " and ");
			}
			
			if (resultStatus != null && resultStatus != "") {
				Long status = Long.valueOf(resultStatus);
				searchQuery = searchQuery.concat(" lts.result_status_id= " + status + " and ");
			}
			
		}
		
		if(isUnderApproval) {
			if (testType != null && testType != "") {
				Long typeOfTest = Long.valueOf(testType);
				searchQuery = searchQuery.concat(" lts.test_type_id= " + typeOfTest + " and ");
			}
		
		}
		
		searchQuery = searchQuery.concat(" lts.is_delete = false ");

		searchQueryList.add(searchQuery);

		return searchQueryList;
	}

}
