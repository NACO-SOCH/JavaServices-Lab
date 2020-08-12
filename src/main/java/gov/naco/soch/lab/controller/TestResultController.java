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

import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.dto.TestSamplesResponseDto;
import gov.naco.soch.lab.service.TestResultService;

@RestController
@RequestMapping("/testresults")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TestResultController {

	private static final Logger logger = LoggerFactory.getLogger(TestResultController.class);

	@Autowired
	private TestResultService testResultService;

	@GetMapping("/list/{labId}")
	public TestSamplesResponseDto fetchTestResultsList(@PathVariable("labId") Long labId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		logger.info("fetchTestResultsList method is invoked");
		return testResultService.fetchTestResultsList(labId, pageNo, pageSize);
	}

	@GetMapping("/underapproval/{labId}")
	public TestSamplesResponseDto fetchTestResultsUnderApproval(@PathVariable("labId") Long labId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		logger.info("fetchTestResultsUnderApproval is invoked!");
		return testResultService.fetchTestResultsUnderApproval(labId, pageNo, pageSize);
	}

	@PostMapping("/approve/{labInchargeId}")
	public List<TestResultDto> approveTestResults(@PathVariable("labInchargeId") Long labInchargeId,
			@RequestBody List<TestResultDto> testResultList) {
		logger.info("approveTestResults is invoked!");
		return testResultService.approveTestResults(labInchargeId, testResultList);
	}

	@PostMapping("/reject/{labInchargeId}")
	public List<TestResultDto> rejectTestResults(@PathVariable("labInchargeId") Long labInchargeId,
			@RequestBody List<TestResultDto> testResultList) {
		logger.info("rejectTestResults is invoked!");
		return testResultService.rejectTestResults(labInchargeId, testResultList);
	}

	@GetMapping("advance/search/{labId}")
	public TestSamplesResponseDto getRecordResultsListByAdvanceSearch(@PathVariable("labId") Long labId,
			@RequestParam Map<String, String> searchValue) {
		logger.info("inside record results list by advance search");
		return testResultService.getTestResultsListByAdvanceSearch(labId, searchValue);
	}

	@GetMapping("/underapproval/advance/search/{labId}")
	public TestSamplesResponseDto getRecordResultsUnderApprovalAdvanceSearch(@PathVariable("labId") Long labId,
			@RequestParam Map<String, String> searchValue) {
		return testResultService.getRecordResultsUnderApprovalAdvanceSearch(labId, searchValue);
	}
}
