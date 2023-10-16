package gov.naco.soch.lab.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.dto.LoginResponseDto;
import gov.naco.soch.enums.FacilityTypeEnum;
import gov.naco.soch.lab.dto.TestRecordResultDto;
import gov.naco.soch.lab.dto.TestResultDto;
import gov.naco.soch.lab.dto.TestSamplesResponseNewDTO;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.TestDetailsGraphRepository;
import gov.naco.soch.util.UserUtils;

public class TestRecordService {
	
	@Autowired
	private TestDetailsGraphRepository testDetailsGraphRepository;
	
	@Autowired
	private LabTestSampleRepository labTestSampleRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(TestResultService.class);
	
	public TestSamplesResponseNewDTO getRecordResultsList(Long labId, Integer pageNo, Integer pageSize) {

		logger.debug("In getRecordResultsList() of RecordResultsService");
		logger.info("inside service class function");
		TestSamplesResponseNewDTO dto = new TestSamplesResponseNewDTO();
		Pageable paging = PageRequest.of(pageNo, pageSize);

		List<TestRecordResultDto> testResultDto = new ArrayList<>();
		logger.info("Query run!!"+testDetailsGraphRepository.findSamplesToRecordResult(labId, paging));
		

		List<Object[]> obj = testDetailsGraphRepository.findSamplesToRecordResult(labId, paging);
		logger.info("Query run!!"+ obj.toString());
		
//		testResultDto = obj.stream().map(s -> TestResultMapper.mapToTestResultDto1(s))
//		.collect(Collectors.toList());
		
		testResultDto = obj.stream()
			    .map(dto1 -> {
			    	TestRecordResultDto mapper = new TestRecordResultDto();
		        	try {
		        	//important
//		        	mapper.setLabId(dto1.getId());
//		    		mapper.setBatchId(dto1.getBatchId());
//		            mapper.setId(dto1.getId());
//		            mapper.setCreatedBy(dto1.getCreatedBy());
//		            mapper.setCreatedTime(dto1.getCreatedTime().toLocalDate());
//		            mapper.setModifiedBy(dto1.getModifiedBy());
//		            mapper.setModifiedTime(dto1.getModifiedTime().toLocalDate());
//		            mapper.setIsUndone(dto1.getIsUndone());
//		            mapper.setArtcSampleStatus(dto1.getArtcSampleStatus());
//		            mapper.setAuthorizerId(dto1.getAuthorizerId());
		    		
		        	}catch(Exception ex) {
		        		ex.getMessage();
		        	}
		            return mapper;
		        })
		        .collect(Collectors.toList());
//			

//			testResultDto = obj.stream()
//				    .map(TestResultMapper::mapToTestResultDto1)
//				    .collect(Collectors.toList());

			LoginResponseDto currentUser = UserUtils.getLoggedInUserDetails();
			if (FacilityTypeEnum.VL_PUBLIC.getFacilityType().equals(currentUser.getFacilityTypeId())) {
				fetchVLTestCount1(testResultDto);
			}

			if (FacilityTypeEnum.LABORATORY_EID.getFacilityType().equals(currentUser.getFacilityTypeId())) {
//				fetchIctcInfantDetails(testResultDto);
//				findPreviousDBSDetails(testResultDto);
			}

//			testResultDto = testResultDto.stream().sorted(Comparator.comparing(TestResultDto::getBatchId).reversed())
//					.collect(Collectors.toList());
			testResultDto = testResultDto.stream()
				    .sorted(Comparator.comparing(TestRecordResultDto::getBatchId).reversed())
				    .collect(Collectors.toList());

			dto.setSamples(testResultDto);
//			dto.setTotalCount(obj.getTotalElements());
		
		dto.setPageNumber(pageNo);
		dto.setCurrentCount(pageSize);
		return dto;
	}

	void fetchVLTestCount(List<TestResultDto> testResultDto) {
		if (!CollectionUtils.isEmpty(testResultDto)) {
			testResultDto.forEach(s -> {
				Long count = labTestSampleRepository.getVLTestCountOfBeneficiary(s.getBeneficiaryId());
				s.setVlTestCount(count);
			});
		}
	}
	
	void fetchVLTestCount1(List<TestRecordResultDto> testResultDto) {
		if (!CollectionUtils.isEmpty(testResultDto)) {
			testResultDto.forEach(s -> {
				Long count = labTestSampleRepository.getVLTestCountOfBeneficiary(s.getBeneficiaryId());
//				s.setVlTestCount(count);
			});
		}
	}

}
