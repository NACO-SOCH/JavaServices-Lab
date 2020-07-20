package gov.naco.soch.lab.service;

import java.util.ArrayList;
import java.util.List;

import gov.naco.soch.lab.dto.CDFourTestCountDetailsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.naco.soch.lab.dto.AdherenceDetailsDto;
import gov.naco.soch.lab.dto.BeneficiaryTestDetailsDto;
import gov.naco.soch.lab.dto.VLTestCountDetailsDto;
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
			List<VLTestCountDetailsDto> vlTestCountDetailsDtoList = new ArrayList<VLTestCountDetailsDto>();
			for(TestCountProjection vlTestCount:artBeneficiaryTestCountProjection) {
				VLTestCountDetailsDto vlTestCountDetailsDto = new VLTestCountDetailsDto();
				vlTestCountDetailsDto.setYear(vlTestCount.getYear());
				vlTestCountDetailsDto.setCount(vlTestCount.getValue());
				vlTestCountDetailsDtoList.add(vlTestCountDetailsDto);
			}
			beneficiaryTestDetailsDto.setVlTestCountDetails(vlTestCountDetailsDtoList);
		}

		List<TestCountProjection> artBeneficiaryCDFourTestCountProjection =
				testDetailsGraphRepository.getCDFourTestCountDetails(beneficiaryId,facilityId);
		if(artBeneficiaryCDFourTestCountProjection !=null && artBeneficiaryCDFourTestCountProjection.size()>0 ) {
			List<CDFourTestCountDetailsDto> cdFourTestCountDetailsDtoList = new ArrayList<CDFourTestCountDetailsDto>();
			for(TestCountProjection vlTestCount:artBeneficiaryCDFourTestCountProjection) {
				CDFourTestCountDetailsDto cdFourTestCountDetailsDto = new CDFourTestCountDetailsDto();
				cdFourTestCountDetailsDto.setYear(vlTestCount.getYear());
				cdFourTestCountDetailsDto.setCount(vlTestCount.getValue());
				cdFourTestCountDetailsDtoList.add(cdFourTestCountDetailsDto);
			}
			beneficiaryTestDetailsDto.setCdfourTestCountDetails(cdFourTestCountDetailsDtoList);
		}
		
		List<TestCountProjection> adherenceTestCountProjection = 
				testDetailsGraphRepository.getAdherenceCountDetails(beneficiaryId,facilityId);
		if(artBeneficiaryTestCountProjection !=null && artBeneficiaryTestCountProjection.size()>0 ) {
			List<AdherenceDetailsDto> adherenceDetailsDtoList = new ArrayList<AdherenceDetailsDto>();
			for(TestCountProjection adherenceCount:adherenceTestCountProjection) {
				AdherenceDetailsDto adherenceDetailsDto = new AdherenceDetailsDto();
				adherenceDetailsDto.setYear(adherenceCount.getYear());
				adherenceDetailsDto.setCount(adherenceCount.getValue());
				adherenceDetailsDtoList.add(adherenceDetailsDto);
			}
			beneficiaryTestDetailsDto.setAdherenceDetails(adherenceDetailsDtoList);
		}
		
		
		return beneficiaryTestDetailsDto;
	}

}
