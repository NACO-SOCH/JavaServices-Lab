package gov.naco.soch.lab.controller;

import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.dto.TestSamplesResponseDto;
import gov.naco.soch.lab.service.RecordResultsService;

@RestController
@RequestMapping("/recordresults")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecordResultsController {

	private static final Logger logger = LoggerFactory.getLogger(RecordResultsController.class);

	@Autowired
	private RecordResultsService recordResultsService;

	@PostMapping("/save/{sampleId}")
	public @ResponseBody TestResultDto saveRecordResult(@PathVariable("sampleId") Long sampleId,
			@Valid @RequestBody TestResultDto labTestSampleDto) {
		logger.debug("saveRecordResult is invoked!");
		return recordResultsService.saveRecordResult(sampleId, labTestSampleDto);
	}

	@GetMapping("/list/{labId}")
	public TestSamplesResponseDto getRecordResultsList(@PathVariable("labId") Long labId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		logger.debug("getRecordResultsList is invoked!");
		return recordResultsService.getRecordResultsList(labId, pageNo, pageSize);
	}

	@GetMapping("/artclist/{artcId}")
	public List<TestResultDto> getRecordResultsArtcList(@PathVariable("artcId") Long artcId) {
		logger.debug("getRecordResultsArtcList is invoked!");
		return recordResultsService.getRecordResultsArtcList(artcId);
	}

	@GetMapping("advance/search/{labId}")
	public TestSamplesResponseDto getRecordResultsListByAdvanceSearch(@PathVariable("labId") Long labId,
			@RequestParam Map<String, String> searchValue) {
		logger.info("inside record results list by advance search");
		return recordResultsService.getRecordResultsListByAdvanceSearch(labId, searchValue);
	}

}
