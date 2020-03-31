package gov.naco.soch.lab.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.service.TestResultsService;
@RestController
@RequestMapping("/testresults")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestResultsController {
	private static final Logger logger = LoggerFactory.getLogger(TestResultsController.class);

	@Autowired
	private TestResultsService testResultService;	

	@GetMapping("/getTestResults/{labId}") 
	public List<TestResultDto> getTestResultDetails(@PathVariable(value = "labId") Long labId) {
		logger.debug("getTestResultDetails is invoked!"); 
		return testResultService.getTestResultDetails(labId); 
	}
}

