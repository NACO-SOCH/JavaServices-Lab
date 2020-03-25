package gov.naco.soch.lab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.service.LabTechService;

@RestController
@RequestMapping("/labtech")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LabTechController {

	private static final Logger logger = LoggerFactory.getLogger(LabTechController.class);

	@Autowired
	private LabTechService labTechService;

}
