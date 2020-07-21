package gov.naco.soch.lab.service;

import java.util.ArrayList;
import java.util.List;

import gov.naco.soch.lab.dto.CDFourTestCountDetailsDto;
import gov.naco.soch.lab.dto.TestDetailsHeaderDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gov.naco.soch.lab.dto.BeneficiaryTestDetailsDto;
import gov.naco.soch.lab.dto.VLTestCountDetailsDto;
import gov.naco.soch.projection.ArtBeneficiaryTestCountProjection;
import gov.naco.soch.projection.TestCountDetailsProjection;
import gov.naco.soch.repository.ArtBeneficiaryTestDetailsRepository;

@Service
public class TestDetailsGraphService {
	
	@Autowired
	private ArtBeneficiaryTestDetailsRepository artBeneficiaryTestDetailsRepository;

	public BeneficiaryTestDetailsDto getBeneficiaryTestDetails(Long beneficiaryId, Long facilityId) {
		
		BeneficiaryTestDetailsDto beneficiaryTestDetailsDto = new BeneficiaryTestDetailsDto();
		
		List<ArtBeneficiaryTestCountProjection> artBeneficiaryTestCountProjection = 
				artBeneficiaryTestDetailsRepository.getVLTestCountDetails(beneficiaryId,facilityId);
		if(artBeneficiaryTestCountProjection !=null && artBeneficiaryTestCountProjection.size()>0 ) {
			List<VLTestCountDetailsDto> vlTestCountDetailsDtoList = new ArrayList<VLTestCountDetailsDto>();
			for(ArtBeneficiaryTestCountProjection vlTestCount:artBeneficiaryTestCountProjection) {
				VLTestCountDetailsDto vlTestCountDetailsDto = new VLTestCountDetailsDto();
				vlTestCountDetailsDto.setYear(vlTestCount.getYear());
				vlTestCountDetailsDto.setCount(vlTestCount.getValue());
				vlTestCountDetailsDtoList.add(vlTestCountDetailsDto);
			}
			beneficiaryTestDetailsDto.setVlTestCountDetails(vlTestCountDetailsDtoList);
		}

		List<ArtBeneficiaryTestCountProjection> artBeneficiaryCDFourTestCountProjection =
				artBeneficiaryTestDetailsRepository.getCDFourTestCountDetails(beneficiaryId,facilityId);
		if(artBeneficiaryCDFourTestCountProjection !=null && artBeneficiaryCDFourTestCountProjection.size()>0 ) {
			List<CDFourTestCountDetailsDto> cdFourTestCountDetailsDtoList = new ArrayList<CDFourTestCountDetailsDto>();
			for(ArtBeneficiaryTestCountProjection vlTestCount:artBeneficiaryCDFourTestCountProjection) {
				CDFourTestCountDetailsDto cdFourTestCountDetailsDto = new CDFourTestCountDetailsDto();
				cdFourTestCountDetailsDto.setYear(vlTestCount.getYear());
				cdFourTestCountDetailsDto.setCount(vlTestCount.getValue());
				cdFourTestCountDetailsDtoList.add(cdFourTestCountDetailsDto);
			}
			beneficiaryTestDetailsDto.setCdfourTestCountDetails(cdFourTestCountDetailsDtoList);
		}
		
		TestCountDetailsProjection testResultHeaderObject = artBeneficiaryTestDetailsRepository.getGraphHeaderCounts(beneficiaryId,facilityId);
		if(!testResultHeaderObject.equals(null) ) {
			
			
		}
		
		
		return beneficiaryTestDetailsDto;
	}

}
