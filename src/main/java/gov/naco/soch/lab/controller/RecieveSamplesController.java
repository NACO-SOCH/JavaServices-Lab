package gov.naco.soch.lab.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.service.ReceiveSamplesService;

@RestController
@RequestMapping("/receivesamples")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecieveSamplesController {

	private static final Logger logger = LoggerFactory.getLogger(RecieveSamplesController.class);

	@Autowired
	private ReceiveSamplesService receiveSamplesService;

	@GetMapping("/list/{labId}")
	public List<LabTestSampleBatchDto> fetchReceiveSamplesList(@PathVariable("labId") Long labId) {
		logger.info("fetchReceiveSamplesList method is invoked");
		return receiveSamplesService.fetchReceiveSamplesList(labId);
	}

	@PostMapping("/{batchId}")
	public LabTestSampleBatchDto saveReceivedSamples(@PathVariable("batchId") Long batchId,
			@RequestBody LabTestSampleBatchDto labTestSampleBatchDto) {
		logger.info("saveReceivedSamples method is invoked");
		labTestSampleBatchDto = receiveSamplesService.saveReceivedSamples(batchId, labTestSampleBatchDto);
		return labTestSampleBatchDto;
	}
	
	@GetMapping("advance/search/{labId}")
	public List<LabTestSampleBatchDto> getReceiveSamplesListByAdvanceSearch(@PathVariable("labId") Long labId,@RequestParam Map<String, String> searchValue) {
		logger.info("inside receive sample list by advance search");
		return receiveSamplesService.getReceiveSamplesListByAdvanceSearch(labId,searchValue);
	}
	
	@PostMapping("undodispatch/{batchId}")
	public void undoDispatchedSample(@PathVariable("batchId") Long batchId) {
		logger.debug("undoDispatchedSample is invoked!");
		receiveSamplesService.undoDispatchedSample(batchId);
	}
}
