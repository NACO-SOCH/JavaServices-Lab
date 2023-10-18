package gov.naco.soch.lab.controller;

<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
=======

import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
>>>>>>> d24c73822b38325e281954ce4c24abfee2324ead

import javax.validation.Valid;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.MHLBarcodeValidationDto;
import gov.naco.soch.lab.dto.PatientLoadDto;
import gov.naco.soch.lab.dto.RecordResultDto;
import gov.naco.soch.lab.service.MHLService;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.springframework.http.HttpHeaders;
/**
 * Controller class for managing MHL related APIs
 *
 */
@RestController
@RequestMapping("/mhl")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MHLController {

	private static final Logger logger = LoggerFactory.getLogger(MHLController.class);

	@Autowired
	private MHLService mhlService;

	// API 1.https://ims.naco.gov.in/api/vload_sample_detail
	@PostMapping(value = "/vload_sample_detail", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<Object> getViralLoadSampleDetail(MHLBarcodeValidationDto mhlBarcodeValidationDto) {
		logger.debug("getViralLoadSampleDetail() method is invoked");
		ResponseEntity<Object> mhlSampleDetailDto = mhlService.getViralLoadSampleDetail(mhlBarcodeValidationDto);
		return mhlSampleDetailDto;

	}

	// API 2.https://ims.naco.gov.in/api/vload_record_result
	@PostMapping(value = "/vload_record_result", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<Object> recordResult(@Valid RecordResultDto recordResultDto) {
		logger.debug("recordResult() method is invoked");
		ResponseEntity<Object> mhlSampleDetailDto = mhlService.recordResult(recordResultDto);
		return mhlSampleDetailDto;
	}

	// API 3.https://ims.naco.gov.in/api/vl_patient_load
	@PostMapping(value = "/vl_patient_load", consumes = { MediaType.APPLICATION_FORM_URLENCODED_VALUE })
	public ResponseEntity<Object> getVLPatientLoad(@Valid PatientLoadDto patientLoadDto) {
		logger.debug("getVLPatientLoad() method is invoked");
		ResponseEntity<Object> loadDetails = mhlService.getVLPatientLoad(patientLoadDto);
		logger.debug("getVLPatientLoad() method returns with parameter {}", loadDetails);
		return loadDetails;
	}
	
	@GetMapping("/loginCount")
	public ResponseEntity<List<Object[]>> getloginCount(
		@RequestParam Integer stateId,
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam Integer facilityId,
	    @RequestParam Integer facilityTypeId,
	    @RequestParam Integer DistrictId) throws ParseException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    List<Object[]> allData = new ArrayList<>(mhlService.getLoginCount(stateId, parsedStartDate, parsedEndDate, facilityId, facilityTypeId, DistrictId));

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(allData.size()));

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(allData);
	}
	
<<<<<<< HEAD
=======
//	@GetMapping("/ComprehensiveloginReport")
//	public ResponseEntity<List<Object[]>> getloginReport(
//	    @RequestParam String startDate,
//	    @RequestParam String endDate) throws ParseException {
//
//	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//	    Date parsedStartDate = dateFormat.parse(startDate);
//	    Date parsedEndDate = dateFormat.parse(endDate);
//
//	    List<Object[]> allData = new ArrayList<>(mhlService.getLoginReport(parsedStartDate, parsedEndDate));
//
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.add("X-Total-Records", String.valueOf(allData.size()));
//
//	    return ResponseEntity.ok()
//	            .headers(headers)
//	            .body(allData);
//	}
	
	@GetMapping("/ComprehensiveloginReport")
	public ResponseEntity<byte[]> getloginReport(
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    
	    List<Object[]> allData = (mhlService.getLoginReport(parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Comprehensive_Data_Report.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Comprehensive_Data_Report.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet");

	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        // Set numeric cell value and apply numeric cell style
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {
	                        // Set string cell value
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                    // Handle the case where cellData is null
	                    cell.setCellValue(""); // or set a default value
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	@GetMapping("/ComprehensiveloginReport/Rolewise")
	public ResponseEntity<byte[]> getloginReportRole(
		@RequestParam(required = false) Integer stateId,
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);
	    
	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);

	    List<Object[]> allData = (mhlService.getLoginReportRole(stateId, parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Comprehensive_Data_RoleWise.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Comprehensive_Data_RoleWise.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet");

	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        // Set numeric cell value and apply numeric cell style
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {
	                        // Set string cell value
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                    // Handle the case where cellData is null
	                    cell.setCellValue(""); // or set a default value
	                }
	            }
	        }
	        
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        
	        workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	@GetMapping("/ComprehensiveloginReport/SACS")
	public ResponseEntity<byte[]> getloginReportRole(
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    
	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    
	    List<Object[]> allData = (mhlService.getLoginReportSacs(parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Comprehensive_Data_Report_SACS.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Comprehensive_Data_Report_SACS.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet");
	        
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	private String formatDate(Date date) {
	    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    return dateFormatter.format(date);
	}
	

	
	@GetMapping("/MasterLine")
	public ResponseEntity<byte[]> getMasterLine(
		@RequestParam Integer stateId,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    
	    List<Object[]> allData = (mhlService.getMasterLine(stateId))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Master_Line_List.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Master_Line_List.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet1");
	        
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	
	
>>>>>>> d24c73822b38325e281954ce4c24abfee2324ead
	@GetMapping("facilities")
    public ResponseEntity<List<Object[]>> getFacilities(
            @RequestParam Long stateId,
            @RequestParam Long facilityTypeId,
            @RequestParam Long DistrictId
    ) {
<<<<<<< HEAD
        List<Object[]> facilities = mhlService.getFacilities(stateId, facilityTypeId, DistrictId);
        return ResponseEntity.ok(facilities);
    }
=======
        List<Object[]> facilities = mhlService.getFacilities(stateId, facilityTypeId,DistrictId);
        return ResponseEntity.ok(facilities);
    }
	
	
	@GetMapping("/FacilityLine")
	public ResponseEntity<byte[]> getFacilityLine(
		@RequestParam Integer stateId,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    
	    List<Object[]> allData = (mhlService.getFacilityLine(stateId))
	            .collect(Collectors.toList());

	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);

	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Facility_Line_List.xlsx");

	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Facility_Line_List.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {

	        Sheet sheet = workbook.getSheet("Sheet1");
	        
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
	
	
	@GetMapping("/dispensationReport")
	public ResponseEntity<byte[]> getdispensationReport(
		@RequestParam Integer facilityId,
	    @RequestParam String startDate,
	    @RequestParam String endDate,
	    @RequestParam(defaultValue = "0") Integer page,
	    @RequestParam(defaultValue = "1000") Integer pageSize) throws ParseException, IOException {

	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    Date parsedStartDate = dateFormat.parse(startDate);
	    Date parsedEndDate = dateFormat.parse(endDate);

	    logger.info("1");
	    SimpleDateFormat currentDateFormatter = new SimpleDateFormat("dd-MMM-yyyy");
	    String currentDate = currentDateFormatter.format(new Date());

	    String reportPeriod = "Report Period : " + formatDate(parsedStartDate) + " To " + formatDate(parsedEndDate);
	    logger.info("2");
	    List<Object[]> allData = (mhlService.getdispensationReport(facilityId, parsedStartDate, parsedEndDate))
	            .collect(Collectors.toList());
	    logger.info("3");
	    int totalRecords = allData.size();
	    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

	    Stream<Object[]> chunkDataStream = allData.stream()
	            .skip(page * pageSize)
	            .limit(totalRecords);
	    logger.info("4");
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("X-Total-Records", String.valueOf(totalRecords));
	    headers.add("X-Total-Pages", String.valueOf(totalPages));
	    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	    headers.setContentDispositionFormData("attachment", "Dispensation_List.xlsx");
	    logger.info("5");
	    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

	    try (InputStream templateStream = getClass().getResourceAsStream("/Dispensation_List.xlsx");
	         Workbook workbook = WorkbookFactory.create(templateStream)) {
	    	
	    	logger.info("6");	 
	    	
	    	Sheet sheet = workbook.getSheet("Sheet1");    
	        int rowIdx = sheet.getLastRowNum() + 1;
	        CellStyle numericCellStyle = workbook.createCellStyle();
	        DataFormat dataFormat = workbook.createDataFormat();
	        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format

	        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
	            Row row = sheet.createRow(rowIdx++);
	            int cellIdx = 0;
	            for (Object cellData : rowData) {
	                Cell cell = row.createCell(cellIdx++);
	                if (cellData != null) {
	                    if (cellData instanceof Number) {
	                        cell.setCellValue(((Number) cellData).doubleValue());
	                        cell.setCellStyle(numericCellStyle);
	                    } else {	                        
	                        cell.setCellValue(cellData.toString());
	                    }
	                } else {
	                   
	                    cell.setCellValue(""); 
	                }
	            }
	        }
	        Row paramsRow = sheet.createRow(rowIdx++ + 2);
	        paramsRow.createCell(0).setCellValue(reportPeriod);

	        Row currentDateRow = sheet.createRow(rowIdx++);
	        currentDateRow.createCell(0).setCellValue("Report Downloaded On : " + currentDate);
	        workbook.setForceFormulaRecalculation(true); 

	        workbook.write(outputStream);
	    }

	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(outputStream.toByteArray());
	}
	
>>>>>>> d24c73822b38325e281954ce4c24abfee2324ead

}
