package gov.naco.soch.lab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gov.naco.soch.lab.service.LabInchargeService;

@RestController
@RequestMapping("/labincharge")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LabInchargeController {

	private static final Logger logger = LoggerFactory.getLogger(LabInchargeController.class);

	@Autowired
	private LabInchargeService labInchargeService;

}
