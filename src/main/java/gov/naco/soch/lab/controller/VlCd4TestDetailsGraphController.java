package gov.naco.soch.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.BeneficiaryTestDetailsDto;
import gov.naco.soch.lab.service.TestDetailsGraphService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class VlCd4TestDetailsGraphController {
	
	@Autowired
	private TestDetailsGraphService artBeneficiaryTestDetailsService;
	
	@GetMapping("/get/testdetails/{beneficiaryId}/{facilityId}")
	public ResponseEntity<BeneficiaryTestDetailsDto> getBeneficiaryTestDetails(
			@PathVariable("beneficiaryId") Long beneficiaryId, @PathVariable("facilityId") Long facilityId) {
		BeneficiaryTestDetailsDto beneficiaryTestDetailsDto = new BeneficiaryTestDetailsDto();
		try {
			beneficiaryTestDetailsDto = artBeneficiaryTestDetailsService.getBeneficiaryTestDetails(beneficiaryId,facilityId);
		} catch (Exception e) {
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<BeneficiaryTestDetailsDto>(beneficiaryTestDetailsDto, HttpStatus.OK);
	}

}
