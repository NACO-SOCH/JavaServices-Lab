package gov.naco.soch.lab.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.LabTestSampleDto;
import gov.naco.soch.lab.service.RecordResultService;
@RestController
@RequestMapping("/recordresult")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RecordResultController {
	
	private static final Logger logger = LoggerFactory.getLogger(RecordResultController.class);

	@Autowired
	private RecordResultService recordResultService;

	@PostMapping("/saveSampleResult")
	public @ResponseBody boolean saveLabSampleResult(
			@Valid @RequestBody LabTestSampleDto labTestSampleDto) {
		logger.debug("Entering into method saveLabSampleResult with labTestSampleDto->{}:",	labTestSampleDto);
		boolean isSaved = recordResultService.saveLabSampleResult(labTestSampleDto);
		return isSaved;
	}
}
