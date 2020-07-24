package gov.naco.soch.lab.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gov.naco.soch.lab.dto.TestDetailsHeaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import gov.naco.soch.lab.dto.TestDetailsBodyDto;
import gov.naco.soch.lab.dto.BeneficiaryTestDetailsDto;
import gov.naco.soch.projection.TestCountHeaderProjection;
import gov.naco.soch.projection.TestCountProjection;
import gov.naco.soch.repository.TestDetailsGraphRepository;

@Service
public class TestDetailsGraphService {
	
	@Autowired
	private TestDetailsGraphRepository testDetailsGraphRepository;

	public BeneficiaryTestDetailsDto getBeneficiaryTestDetails(Long beneficiaryId, Long facilityId) {
		
		BeneficiaryTestDetailsDto beneficiaryTestDetailsDto = new BeneficiaryTestDetailsDto();

		List<TestCountProjection> artBeneficiaryTestCountProjection = 
				testDetailsGraphRepository.getVLTestCountDetails(beneficiaryId,facilityId);
		if(artBeneficiaryTestCountProjection !=null && artBeneficiaryTestCountProjection.size()>0 ) {
			List<TestDetailsBodyDto> vlTestCountDetailsDtoList = new ArrayList<TestDetailsBodyDto>();
			for(TestCountProjection vlTestCount:artBeneficiaryTestCountProjection) {
				TestDetailsBodyDto vlTestCountDetailsDto = new TestDetailsBodyDto();
				vlTestCountDetailsDto.setYear(vlTestCount.getYear());
				vlTestCountDetailsDto.setCount(vlTestCount.getValue());
				vlTestCountDetailsDtoList.add(vlTestCountDetailsDto);
			}
			beneficiaryTestDetailsDto.setVlTestCountDetails(vlTestCountDetailsDtoList);
		}

		List<TestCountProjection> artBeneficiaryCDFourTestCountProjection =
				testDetailsGraphRepository.getCDFourTestCountDetails(beneficiaryId,facilityId);
		if(artBeneficiaryCDFourTestCountProjection !=null && artBeneficiaryCDFourTestCountProjection.size()>0 ) {
			List<TestDetailsBodyDto> cdFourTestCountDetailsDtoList = new ArrayList<TestDetailsBodyDto>();
			for(TestCountProjection vlTestCount:artBeneficiaryCDFourTestCountProjection) {
				TestDetailsBodyDto cdFourTestCountDetailsDto = new TestDetailsBodyDto();
				cdFourTestCountDetailsDto.setYear(vlTestCount.getYear());
				cdFourTestCountDetailsDto.setCount(vlTestCount.getValue());
				cdFourTestCountDetailsDtoList.add(cdFourTestCountDetailsDto);
			}
			beneficiaryTestDetailsDto.setCdfourTestCountDetails(cdFourTestCountDetailsDtoList);
		}
		

		List<TestCountProjection> adherenceTestCountProjection = 
				testDetailsGraphRepository.getAdherenceCountDetails(beneficiaryId,facilityId);
		if(artBeneficiaryTestCountProjection !=null && artBeneficiaryTestCountProjection.size()>0 ) {
			List<TestDetailsBodyDto> adherenceDetailsDtoList = new ArrayList<TestDetailsBodyDto>();
			for(TestCountProjection adherenceCount:adherenceTestCountProjection) {
				TestDetailsBodyDto adherenceDetailsDto = new TestDetailsBodyDto();
				adherenceDetailsDto.setYear(adherenceCount.getYear());
				adherenceDetailsDto.setCount(adherenceCount.getValue());
				adherenceDetailsDtoList.add(adherenceDetailsDto);
			}
			beneficiaryTestDetailsDto.setAdherenceDetails(adherenceDetailsDtoList);
		}
		
		TestDetailsHeaderDto testResultHeaderObject = getHeaderDetails(beneficiaryId,facilityId);
		
		beneficiaryTestDetailsDto.setTestDetailsHeaderDto(testResultHeaderObject);
	}

	private TestDetailsHeaderDto getHeaderDetails(Long beneficiaryId, Long facilityId) {
        
		TestDetailsHeaderDto testDetailsHeaderDto = new TestDetailsHeaderDto();
		Date artStartDate = testDetailsGraphRepository.getStartDate(beneficiaryId,facilityId);
		testDetailsHeaderDto.setArtStartDate(artStartDate);
		List<Date> currentAndpreviousDates = testDetailsGraphRepository.getDates(beneficiaryId,facilityId);
		if(!currentAndpreviousDates.equals(null) && !currentAndpreviousDates.isEmpty()) {
			if(currentAndpreviousDates.size() == 2) {
				
				int currentVlCount = testDetailsGraphRepository.getCurrentVlCount(beneficiaryId,facilityId,currentAndpreviousDates.get(0));
				int previousVlCount = testDetailsGraphRepository.getCurrentVlCount(beneficiaryId,facilityId,currentAndpreviousDates.get(1));
				testDetailsHeaderDto.setCurrentVlCount(currentVlCount);
				
			}
		}
		
		return testDetailsHeaderDto;
	}

}
