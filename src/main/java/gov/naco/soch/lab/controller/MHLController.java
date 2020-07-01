package gov.naco.soch.lab.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.PatientLoadDto;
import gov.naco.soch.lab.dto.RecordResultDto;
import gov.naco.soch.lab.service.MHLService;

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
	@PostMapping("/vload_sample_detail")
	public ResponseEntity<Object> getViralLoadSampleDetail(
			@Valid @RequestParam(value = "barcode", required = true) String barcode) {
		logger.debug("getViralLoadSampleDetail() method is invoked");
		ResponseEntity<Object> mhlSampleDetailDto = mhlService.getViralLoadSampleDetail(barcode);
		return mhlSampleDetailDto;

	}
	//API 2.https://ims.naco.gov.in/api/vload_record_result
	@PostMapping("/vload_record_result")
	public ResponseEntity<Object> recordResult(
			@Valid@RequestBody RecordResultDto recordResultDto) {
		logger.debug("recordResult() method is invoked");
		ResponseEntity<Object> mhlSampleDetailDto = mhlService.recordResult(recordResultDto);
		return mhlSampleDetailDto;
	}

	// API 3.https://ims.naco.gov.in/api/vl_patient_load
	@PostMapping("/vl_patient_load")
	public ResponseEntity<Object> getVLPatientLoad(@Valid @RequestBody PatientLoadDto patientLoadDto) {
		logger.debug("getVLPatientLoad() method is invoked");
		ResponseEntity<Object> loadDetails = mhlService.getVLPatientLoad(patientLoadDto);
		logger.debug("getVLPatientLoad() method returns with parameter {}", loadDetails);
		return loadDetails;
	}

}
