package gov.naco.soch.lab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.dto.InfantTestHistoryDto;
import gov.naco.soch.lab.service.InfantTestHistoryService;

@RestController
@RequestMapping("/infanttesthistory")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class InfantTestHistoryController {

	private static final Logger logger = LoggerFactory.getLogger(InfantTestHistoryController.class);

	@Autowired
	private InfantTestHistoryService infantTestHistorService;

	@GetMapping("/{infantId}/{labId}")
	public InfantTestHistoryDto fetchInfantTestHistory(@PathVariable("infantId") Long infantId,
			@PathVariable("labId") Long labId) {
		logger.info("fetchInfantTestHistory method is invoked");
		return infantTestHistorService.fetchInfantTestHistory(infantId, labId);
	}
}
