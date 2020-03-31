package gov.naco.soch.lab.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.naco.soch.repository.LabTestSampleBatchRepository;
import gov.naco.soch.repository.LabTestSampleRepository;

@Service
@Transactional
public class LabInchargeService {
	@Autowired
	private LabTestSampleRepository labTestSampleRepository;
	
	@Autowired
	private LabTestSampleBatchRepository labTestSampleBatchRepository;
	private static final Logger logger = LoggerFactory.getLogger(LabInchargeService.class);
	
	
	
	 

}
