package gov.naco.soch.lab.controller;

import java.util.List;
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
	@PreAuthorize("hasAuthority('" + LabAccessCodes.EIDLAB_INFANT_TEST_RESULTS + "') or hasAuthority('"
			+ LabAccessCodes.VL_TEST_RESULTS + "')")
	public TestSamplesResponseDto fetchTestResultsList(@PathVariable("labId") Long labId,
			@RequestParam(required = false) Integer pageNo, @RequestParam(required = false) Integer pageSize, @RequestParam(defaultValue = "modified_time") String sortBy,
			@RequestParam(defaultValue = "desc") String sortType) {
		logger.info("fetchTestResultsList method is invoked");
		return testResultService.fetchTestResultsList(labId, pageNo, pageSize,sortBy,sortType);
	}

	@GetMapping("/underapproval/{labId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.EIDLAB_RESULTS_UNDER_APPROVAL + "') or hasAuthority('"
			+ LabAccessCodes.VL_RESULT_UNDER_APPROVAL + "')")
	public TestSamplesResponseDto fetchTestResultsUnderApproval(@PathVariable("labId") Long labId,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
		logger.info("fetchTestResultsUnderApproval is invoked!");
		return testResultService.fetchTestResultsUnderApproval(labId, pageNo, pageSize);
	}

	@PostMapping("/approve/{labInchargeId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.EIDLAB_RESULTS_UNDER_APPROVAL + "') or hasAuthority('"
			+ LabAccessCodes.VL_RESULT_UNDER_APPROVAL + "')")
	public List<TestResultDto> approveTestResults(@PathVariable("labInchargeId") Long labInchargeId,
			@RequestBody List<TestResultDto> testResultList) {
		logger.info("approveTestResults is invoked!");
		return testResultService.approveTestResults(labInchargeId, testResultList);
	}

	@PostMapping("/reject/{labInchargeId}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.EIDLAB_RESULTS_UNDER_APPROVAL + "') or hasAuthority('"
			+ LabAccessCodes.VL_RESULT_UNDER_APPROVAL + "')")
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
	@PreAuthorize("hasAuthority('" + LabAccessCodes.EIDLAB_RESULTS_UNDER_APPROVAL + "') or hasAuthority('"
			+ LabAccessCodes.VL_RESULT_UNDER_APPROVAL + "')")
	public TestSamplesResponseDto getRecordResultsUnderApprovalAdvanceSearch(@PathVariable("labId") Long labId,
			@RequestParam Map<String, String> searchValue) {
		return testResultService.getRecordResultsUnderApprovalAdvanceSearch(labId, searchValue);
	}

	@GetMapping("/normal/searchby/{labId}/{searchValue}")
	@PreAuthorize("hasAuthority('" + LabAccessCodes.EIDLAB_INFANT_TEST_RESULTS + "') or hasAuthority('"
			+ LabAccessCodes.VL_TEST_RESULTS + "')")
	public TestSamplesResponseDto fetchTestResultsListByNormalSearch(@PathVariable("labId") Long labId,
			@PathVariable("searchValue") String searchValue, @RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "0") Integer pageSize) {
		logger.info("fetchTestResultsList method is invoked");
		return testResultService.fetchTestResultsListByNormalSearch(labId, searchValue, pageNo, pageSize);
	}
}
