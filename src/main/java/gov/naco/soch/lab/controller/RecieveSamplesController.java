package gov.naco.soch.lab.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.constants.LabAccessCodes;
import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.ReceiceSamplesResponseDto;
import gov.naco.soch.lab.service.ReceiveSamplesService;

@RestController
@RequestMapping("/receivesamples")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecieveSamplesController {

	private static final Logger logger = LoggerFactory.getLogger(RecieveSamplesController.class);

	@Autowired
	private ReceiveSamplesService receiveSamplesService;

//	@GetMapping("/list/{labId}")
//	public List<LabTestSampleBatchDto> fetchReceiveSamplesList(@PathVariable("labId") Long labId) {
//		logger.info("fetchReceiveSamplesList method is invoked");
//		return receiveSamplesService.fetchReceiveSamplesList(labId);
//	}

	@GetMapping("/list/{labId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.VL_RECIEVE_SAMPLES + "') or hasAuthority('"
			+ LabAccessCodes.EIDLAB_RECEIVE_SAMPLES + "')")
	public ReceiceSamplesResponseDto fetchReceiveSamplesList(@PathVariable("labId") Long labId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		logger.info("fetchReceiveSamplesList method is invoked");
		return receiveSamplesService.fetchReceiveSamplesList(labId, pageNo, pageSize);
	}

	//sun007
	@PostMapping("/{batchId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.VL_RECIEVE_SAMPLES + "') or hasAuthority('"
			+ LabAccessCodes.EIDLAB_RECEIVE_SAMPLES + "')")
	public LabTestSampleBatchDto saveReceivedSamples(@PathVariable("batchId") Long batchId,
			 @RequestBody LabTestSampleBatchDto labTestSampleBatchDto) {
		logger.info("saveReceivedSamples method is invoked");
		
		System.out.println("print-getReceivedDate => "); // Added by Asjad
		System.out.println(labTestSampleBatchDto.getReceivedDate());
		
		logger.warn("receivedData---1",labTestSampleBatchDto.getReceivedDate());
		logger.info("saveReceivedSamples with parameters->{}", labTestSampleBatchDto);
		labTestSampleBatchDto = receiveSamplesService.saveReceivedSamples(batchId, labTestSampleBatchDto);  // Commented by Asjad
		return labTestSampleBatchDto;
	}

	@GetMapping("advance/search/{labId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.VL_RECIEVE_SAMPLES + "') or hasAuthority('"
			+ LabAccessCodes.EIDLAB_RECEIVE_SAMPLES + "')")
	public ReceiceSamplesResponseDto getReceiveSamplesListByAdvanceSearch(@PathVariable("labId") Long labId,
			@RequestParam Map<String, String> searchValue) {
		logger.info("inside receive sample list by advance search");
		return receiveSamplesService.getReceiveSamplesListByAdvanceSearch(labId, searchValue);
	}

	@PostMapping("undodispatch/{batchId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.VL_RECIEVE_SAMPLES + "') or hasAuthority('"
			+ LabAccessCodes.EIDLAB_RECEIVE_SAMPLES + "')")
	public void undoDispatchedSample(@PathVariable("batchId") Long batchId) {
		logger.debug("undoDispatchedSample is invoked!");
		receiveSamplesService.undoDispatchedSample(batchId);
	}

	@GetMapping("/batch/{batchId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.VL_RECIEVE_SAMPLES + "') or hasAuthority('"
			+ LabAccessCodes.EIDLAB_RECEIVE_SAMPLES + "')")
	public LabTestSampleBatchDto fetchReceiveSamplesByBatchId(@PathVariable("batchId") Long batchId) {
		logger.info("fetchReceiveSamplesByBatchId method is invoked");
		return receiveSamplesService.fetchReceiveSamplesByBatchId(batchId);
	}
}
