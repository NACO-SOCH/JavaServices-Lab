package gov.naco.soch.lab.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.LabTestSampleBatchDto;
import gov.naco.soch.lab.service.RecieveSamplesService;

@RestController
@RequestMapping("/recievesamples")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecieveSamplesController {

	private static final Logger logger = LoggerFactory.getLogger(LabTechController.class);

	@Autowired
	private RecieveSamplesService recieveSamplesService;

	@GetMapping("/list/{labId}")
	public List<LabTestSampleBatchDto> fetchRecieveSamplesList(@PathVariable("labId") Long labId) {
		logger.info("fetchRecieveSamplesList method is invoked");
		return recieveSamplesService.fetchRecieveSamplesList(labId);
	}

	@PostMapping("/{batchId}")
	public LabTestSampleBatchDto saveRecievedSamples(@PathVariable("batchId") Long batchId,
			@RequestBody LabTestSampleBatchDto labTestSampleBatchDto) {
		logger.info("saveRecievedSamples method is invoked");
		labTestSampleBatchDto = recieveSamplesService.saveRecievedSamples(labTestSampleBatchDto);
		return labTestSampleBatchDto;
	}
}
