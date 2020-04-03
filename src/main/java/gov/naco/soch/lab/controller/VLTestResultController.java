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

import gov.naco.soch.lab.dto.VLTestResultDto;
import gov.naco.soch.lab.service.VLTestResultService;

@RestController
@RequestMapping("/vltestresults")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VLTestResultController {

	private static final Logger logger = LoggerFactory.getLogger(VLTestResultController.class);

	@Autowired
	private VLTestResultService vlTestResultService;

	@GetMapping("/list/{labId}")
	public List<VLTestResultDto> fetchVLTestResultsList(@PathVariable("labId") Long labId) {
		logger.info("fetchVLTestResultsList method is invoked");
		return vlTestResultService.fetchVLTestResultsList(labId);
	}

	@GetMapping("/underapproval/{labId}")
	public List<VLTestResultDto> fetchVLTestResultsUnderApproval(@PathVariable("labId") Long labId) {
		logger.info("fetchVLTestResultsUnderApproval is invoked!");
		return vlTestResultService.fetchVLTestResultsUnderApproval(labId);
	}

	@PostMapping("/approve/{labInchargeId}")
	public List<VLTestResultDto> approveVLTestResults(@PathVariable("labInchargeId") Long labInchargeId,
			@RequestBody List<VLTestResultDto> vlTestResultList) {
		logger.info("approveVLTestResults is invoked!");
		return vlTestResultService.approveVLTestResults(labInchargeId, vlTestResultList);
	}

	@PostMapping("/reject/{labInchargeId}")
	public List<VLTestResultDto> rejectVLTestResults(@PathVariable("labInchargeId") Long labInchargeId,
			@RequestBody List<VLTestResultDto> vlTestResultList) {
		logger.info("rejectVLTestResults is invoked!");
		return vlTestResultService.rejectVLTestResults(labInchargeId, vlTestResultList);
	}
}
