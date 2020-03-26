package gov.naco.soch.lab.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import gov.naco.soch.lab.dto.LabTestSampleBatchDto;

@Service
@Transactional
public class RecieveSamplesService {

	private static final Logger logger = LoggerFactory.getLogger(RecieveSamplesService.class);

	public List<LabTestSampleBatchDto> fetchRecieveSamplesList(Long labId) {

		return null;
	}

	public LabTestSampleBatchDto saveRecievedSamples(LabTestSampleBatchDto labTestSampleBatchDto) {

		return labTestSampleBatchDto;
	}

}
