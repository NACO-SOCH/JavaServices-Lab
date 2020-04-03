package gov.naco.soch.lab.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.VLTestResultDto;
import gov.naco.soch.lab.service.RecordResultsService;

@RestController
@RequestMapping("/recordresults")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecordResultsController {

	private static final Logger logger = LoggerFactory.getLogger(RecordResultsController.class);

	@Autowired
	private RecordResultsService recordResultsService;

	@PostMapping("/save/{sampleId}")
	public @ResponseBody VLTestResultDto saveRecordResult(@PathVariable("sampleId") Long sampleId,
			@Valid @RequestBody VLTestResultDto labTestSampleDto) {
		logger.debug("saveRecordResult is invoked!");
		return recordResultsService.saveRecordResult(sampleId, labTestSampleDto);
	}

	@GetMapping("/list/{labId}")
	public List<VLTestResultDto> getRecordResultsList(@PathVariable("labId") Long labId) {
		logger.debug("getRecordResultsList is invoked!");
		return recordResultsService.getRecordResultsList(labId);
	}
	
	@GetMapping("/artclist/{artcId}")
	public List<VLTestResultDto> getRecordResultsArtcList(@PathVariable("artcId") Long artcId) {
		logger.debug("getRecordResultsArtcList is invoked!");
		return recordResultsService.getRecordResultsArtcList(artcId);
	}
}
