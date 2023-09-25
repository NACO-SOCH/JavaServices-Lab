package gov.naco.soch.lab.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.text.SimpleDateFormat;

import org.ehcache.shadow.org.terracotta.offheapstore.paging.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Streamable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;
import org.springframework.http.MediaType;

import org.apache.poi.ss.usermodel.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import gov.naco.soch.lab.dto.BeneficiaryTestDetailsDto;
import gov.naco.soch.lab.service.TestDetailsGraphService;
import java.util.ArrayList;
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class VlCd4TestDetailsGraphController {
	
	
	private static final Logger logger = Logger.getLogger(VlCd4TestDetailsGraphController.class.getName());
	  
	@Autowired
	private TestDetailsGraphService artBeneficiaryTestDetailsService;
	
	@GetMapping("/testdetailsgraph/{beneficiaryId}/{facilityId}")
	public ResponseEntity<BeneficiaryTestDetailsDto> getBeneficiaryTestDetails(
			@PathVariable("beneficiaryId") Long beneficiaryId, @PathVariable("facilityId") Long facilityId) {
		BeneficiaryTestDetailsDto beneficiaryTestDetailsDto = new BeneficiaryTestDetailsDto();
		try {
			beneficiaryTestDetailsDto = artBeneficiaryTestDetailsService.getBeneficiaryTestDetails(beneficiaryId,facilityId);
		} catch (Exception e) {
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BeneficiaryTestDetailsDto>(beneficiaryTestDetailsDto, HttpStatus.OK);
	}
	
	
//	@GetMapping("/ictcmpr")
//	public ResponseEntity<byte[]> getTiMpr(
//		    @RequestParam Integer facilityId,
//		    @RequestParam Integer mprMonth,
//		    @RequestParam Integer mprYear,
//		    @RequestParam Integer ictcStateId,
//		    @RequestParam(defaultValue = "0") Integer page,
//		    @RequestParam(defaultValue = "1000") Integer pageSize) throws IOException {
//
//		    List<Object[]> allData = artBeneficiaryTestDetailsService.getMPRData(facilityId, mprMonth, mprYear, ictcStateId)
//		            .collect(Collectors.toList());
//
//		    int totalRecords = allData.size();
//		    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//		    Stream<Object[]> chunkDataStream = allData.stream()
//		            .skip(page * pageSize)
//		            .limit(totalRecords);
//
//		    HttpHeaders headers = new HttpHeaders();
//		    headers.add("X-Total-Records", String.valueOf(totalRecords));
//		    headers.add("X-Total-Pages", String.valueOf(totalPages));
//		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		    headers.setContentDispositionFormData("attachment", "ICTC_MPR.xlsx");
//
//		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//		    try (InputStream templateStream = getClass().getResourceAsStream("/ICTC_MPR.xlsx");
//		         Workbook workbook = WorkbookFactory.create(templateStream)) {
//
//		        Sheet sheet = workbook.getSheet("MPR");
//
//		        int rowIdx = sheet.getLastRowNum() + 1;
//		        CellStyle numericCellStyle = workbook.createCellStyle();
//		        DataFormat dataFormat = workbook.createDataFormat();
//		        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format
//
//		        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
//		            Row row = sheet.createRow(rowIdx++);
//		            int cellIdx = 0;
//		            for (Object cellData : rowData) {
//		                Cell cell = row.createCell(cellIdx++);
//		                if (cellData != null) {
//		                    if (cellData instanceof Number) {
//		                        // Set numeric cell value and apply numeric cell style
//		                        cell.setCellValue(((Number) cellData).doubleValue());
//		                        cell.setCellStyle(numericCellStyle);
//		                    } else {
//		                        // Set string cell value
//		                        cell.setCellValue(cellData.toString());
//		                    }
//		                } else {
//		                    // Handle the case where cellData is null
//		                    cell.setCellValue(""); // or set a default value
//		                }
//		            }
//		        }
//		        workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation
//
//		        workbook.write(outputStream);
//		    }
//
//		    return ResponseEntity.ok()
//		            .headers(headers)
//		            .body(outputStream.toByteArray());
//		}
//	
//	
//	@GetMapping("/VLmpr")
//	public ResponseEntity<byte[]> getVlMpr(
//		    @RequestParam Integer LabId,
//		    @RequestParam Integer mprMonth,
//		    @RequestParam Integer mprYear,
//		    @RequestParam Integer StateId,
//		    @RequestParam(defaultValue = "0") Integer page,
//		    @RequestParam(defaultValue = "1000") Integer pageSize) throws IOException {
//
//		    List<Object[]> allData = artBeneficiaryTestDetailsService.getVLMPRData(LabId, mprMonth, mprYear, StateId)
//		            .collect(Collectors.toList());
//
//		    int totalRecords = allData.size();
//		    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//		    Stream<Object[]> chunkDataStream = allData.stream()
//		            .skip(page * pageSize)
//		            .limit(totalRecords);
//
//		    HttpHeaders headers = new HttpHeaders();
//		    headers.add("X-Total-Records", String.valueOf(totalRecords));
//		    headers.add("X-Total-Pages", String.valueOf(totalPages));
//		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		    headers.setContentDispositionFormData("attachment", "VL_MPR.xlsx");
//
//		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//		    try (InputStream templateStream = getClass().getResourceAsStream("/VL_MPR.xlsx");
//		         Workbook workbook = WorkbookFactory.create(templateStream)) {
//
//		        Sheet sheet = workbook.getSheet("VL");
//
//		        int rowIdx = sheet.getLastRowNum() + 1;
//		        CellStyle numericCellStyle = workbook.createCellStyle();
//		        DataFormat dataFormat = workbook.createDataFormat();
//		        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format
//
//		        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
//		            Row row = sheet.createRow(rowIdx++);
//		            int cellIdx = 0;
//		            for (Object cellData : rowData) {
//		                Cell cell = row.createCell(cellIdx++);
//		                if (cellData != null) {
//		                    if (cellData instanceof Number) {
//		                        // Set numeric cell value and apply numeric cell style
//		                        cell.setCellValue(((Number) cellData).doubleValue());
//		                        cell.setCellStyle(numericCellStyle);
//		                    } else {
//		                        // Set string cell value
//		                        cell.setCellValue(cellData.toString());
//		                    }
//		                } else {
//		                    // Handle the case where cellData is null
//		                    cell.setCellValue(""); // or set a default value
//		                }
//		            }
//		        }
//		        workbook.setForceFormulaRecalculation(true); // Enable automatic formula recalculation
//
//		        workbook.write(outputStream);
//		    }
//
//		    return ResponseEntity.ok()
//		            .headers(headers)
//		            .body(outputStream.toByteArray());
//		}
//	
//	
//	@GetMapping("/CD4mpr")
//	public ResponseEntity<byte[]> getCD4Mpr(
//		    @RequestParam Integer LabId,
//		    @RequestParam Integer mprMonth,
//		    @RequestParam Integer mprYear,
//		    @RequestParam Integer StateId,
//		    @RequestParam(defaultValue = "0") Integer page,
//		    @RequestParam(defaultValue = "1000") Integer pageSize) throws IOException {
//
//		    List<Object[]> allData = artBeneficiaryTestDetailsService.getCD4MPRData(LabId, mprMonth, mprYear, StateId)
//		            .collect(Collectors.toList());
//
//		    int totalRecords = allData.size();
//		    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//		    Stream<Object[]> chunkDataStream = allData.stream()
//		            .skip(page * pageSize)
//		            .limit(totalRecords);
//
//		    HttpHeaders headers = new HttpHeaders();
//		    headers.add("X-Total-Records", String.valueOf(totalRecords));
//		    headers.add("X-Total-Pages", String.valueOf(totalPages));
//		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		    headers.setContentDispositionFormData("attachment", "CD4_MPR.xlsx");
//
//		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//		    try (InputStream templateStream = getClass().getResourceAsStream("/CD4_MPR.xlsx");
//		         Workbook workbook = WorkbookFactory.create(templateStream)) {
//
//		        Sheet sheet = workbook.getSheet("CD4");
//
//		        int rowIdx = sheet.getLastRowNum() + 1;
//		        CellStyle numericCellStyle = workbook.createCellStyle();
//		        DataFormat dataFormat = workbook.createDataFormat();
//		        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); // Set the desired numeric format
//
//		        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
//		            Row row = sheet.createRow(rowIdx++);
//		            int cellIdx = 0;
//		            for (Object cellData : rowData) {
//		                Cell cell = row.createCell(cellIdx++);
//		                if (cellData != null) {
//		                    if (cellData instanceof Number) {
//		                        // Set numeric cell value and apply numeric cell style
//		                        cell.setCellValue(((Number) cellData).doubleValue());
//		                        cell.setCellStyle(numericCellStyle);
//		                    } else {
//		                        // Set string cell value
//		                        cell.setCellValue(cellData.toString());
//		                    }
//		                } else {
//		                    cell.setCellValue(""); 
//		                }
//		            }
//		        }
//		        workbook.setForceFormulaRecalculation(true); 
//
//		        workbook.write(outputStream);
//		    }
//
//		    return ResponseEntity.ok()
//		            .headers(headers)
//		            .body(outputStream.toByteArray());
//		}
//	
//	
//	@GetMapping("/Eidmpr")
//	public ResponseEntity<byte[]> getEidMpr(
//		    @RequestParam Integer LabId,
//		    @RequestParam Integer mprMonth,
//		    @RequestParam Integer mprYear,
//		    @RequestParam Integer StateId,
//		    @RequestParam(defaultValue = "0") Integer page,
//		    @RequestParam(defaultValue = "1000") Integer pageSize) throws IOException {
//
//		    List<Object[]> allData = artBeneficiaryTestDetailsService.getEidMPRData(LabId, mprMonth, mprYear, StateId)
//		            .collect(Collectors.toList());
//
//		    int totalRecords = allData.size();
//		    int totalPages = (int) Math.ceil((double) totalRecords / pageSize);
//
//		    Stream<Object[]> chunkDataStream = allData.stream()
//		            .skip(page * pageSize)
//		            .limit(totalRecords);
//
//		    HttpHeaders headers = new HttpHeaders();
//		    headers.add("X-Total-Records", String.valueOf(totalRecords));
//		    headers.add("X-Total-Pages", String.valueOf(totalPages));
//		    headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//		    headers.setContentDispositionFormData("attachment", "EID_MPR.xlsx");
//
//		    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//		    try (InputStream templateStream = getClass().getResourceAsStream("/EID_MPR.xlsx");
//		         Workbook workbook = WorkbookFactory.create(templateStream)) {
//
//		        Sheet sheet = workbook.getSheet("EID");
//
//		        int rowIdx = sheet.getLastRowNum() + 1;
//		        CellStyle numericCellStyle = workbook.createCellStyle();
//		        DataFormat dataFormat = workbook.createDataFormat();
//		        numericCellStyle.setDataFormat(dataFormat.getFormat("#0")); 
//
//		        for (Object[] rowData : chunkDataStream.collect(Collectors.toList())) {
//		            Row row = sheet.createRow(rowIdx++);
//		            int cellIdx = 0;
//		            for (Object cellData : rowData) {
//		                Cell cell = row.createCell(cellIdx++);
//		                if (cellData != null) {
//		                    if (cellData instanceof Number) {
//		                        cell.setCellValue(((Number) cellData).doubleValue());
//		                        cell.setCellStyle(numericCellStyle);
//		                    } else {
//		                        cell.setCellValue(cellData.toString());
//		                    }
//		                } else {
//		                    cell.setCellValue(""); 
//		                }
//		            }
//		        }
//		        workbook.setForceFormulaRecalculation(true);
//
//		        workbook.write(outputStream);
//		    }
//
//		    return ResponseEntity.ok()
//		            .headers(headers)
//		            .body(outputStream.toByteArray());
//		}
	

}
