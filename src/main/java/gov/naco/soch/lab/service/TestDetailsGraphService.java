
package gov.naco.soch.lab.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import gov.naco.soch.lab.dto.TestDetailsHeaderDto;
import gov.naco.soch.lab.dto.VlValueConstantsForLabDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import gov.naco.soch.lab.dto.TestDetailsBodyDto;
import gov.naco.soch.lab.constants.VlResultTypeConstatnts;
import gov.naco.soch.lab.dto.BeneficiaryTestDetailsDto;
import gov.naco.soch.lab.dto.TestDetailsAdheranceDto;
import gov.naco.soch.projection.TestDetailsAdheranceProjection;
import gov.naco.soch.projection.TestDetailsGraphBodyProjection;
import gov.naco.soch.repository.TestDetailsGraphRepository;

@Service
public class TestDetailsGraphService {

	@Autowired
	private TestDetailsGraphRepository testDetailsGraphRepository;

	public BeneficiaryTestDetailsDto getBeneficiaryTestDetails(Long beneficiaryId, Long facilityId) {
		
		BeneficiaryTestDetailsDto beneficiaryTestDetailsDto = new BeneficiaryTestDetailsDto();
		
		List<TestDetailsGraphBodyProjection> artBeneficiaryVlTestProjection = testDetailsGraphRepository.getVLTestCountDetails(beneficiaryId,facilityId);
		
		if (artBeneficiaryVlTestProjection != null && artBeneficiaryVlTestProjection.size() > 0) { 
			
			List<TestDetailsBodyDto> vlTestCountDetailsDtoList = new ArrayList<TestDetailsBodyDto>();

		for (TestDetailsGraphBodyProjection vlTestCountProjection : artBeneficiaryVlTestProjection) {
			  
			  TestDetailsBodyDto testDetailsBodyDto = new TestDetailsBodyDto();
			  testDetailsBodyDto.setResultId(vlTestCountProjection.getResultId());
			  testDetailsBodyDto.setResultType(vlTestCountProjection.getResultType());
			  testDetailsBodyDto.setResultValue(getScalevalue(vlTestCountProjection.getResultType(), vlTestCountProjection.getResultValue()));
			  testDetailsBodyDto.setResultDate(vlTestCountProjection.getResultDate());
			  
			  vlTestCountDetailsDtoList.add(testDetailsBodyDto);
		  }
		beneficiaryTestDetailsDto.setVlTestCountDetails(vlTestCountDetailsDtoList); 
		
		}
  
     List<TestDetailsGraphBodyProjection> artBeneficiaryCDFourTestCountProjection = testDetailsGraphRepository.getCDFourTestCountDetails(beneficiaryId,facilityId);
     if(artBeneficiaryCDFourTestCountProjection !=null && artBeneficiaryCDFourTestCountProjection.size()>0 ) {
    	 List<TestDetailsBodyDto> cdFourTestCountDetailsDtoList = new ArrayList<TestDetailsBodyDto>();
    	 for(TestDetailsGraphBodyProjection cdFourTestDto : artBeneficiaryCDFourTestCountProjection) {
	    		 TestDetailsBodyDto cdFourTestDetailsDto = new TestDetailsBodyDto();
	    		 cdFourTestDetailsDto.setResultId(cdFourTestDto.getResultId());
	    		 cdFourTestDetailsDto.setResultValue(cdFourTestDto.getResultValue());
	    		 cdFourTestDetailsDto.setResultDate(cdFourTestDto.getResultDate());
	    		 cdFourTestCountDetailsDtoList.add(cdFourTestDetailsDto);
	    		 
    		  }
    	 	beneficiaryTestDetailsDto.setCdfourTestCountDetails(cdFourTestCountDetailsDtoList); 
    	 	
     }
		
		  List<TestDetailsAdheranceProjection> adherenceTestCountProjection = testDetailsGraphRepository.getAdherenceCountDetails(beneficiaryId,facilityId);
		  if(adherenceTestCountProjection !=null && adherenceTestCountProjection.size()>0 ) { 
			  List<TestDetailsAdheranceDto> adherenceDetailsDtoList = new ArrayList<TestDetailsAdheranceDto>();
		  for(TestDetailsAdheranceProjection adherenceCount:adherenceTestCountProjection) {
			  TestDetailsAdheranceDto adherenceDetailsDto = new TestDetailsAdheranceDto();
			  	  adherenceDetailsDto.setResultId(adherenceCount.getResultId());
				  adherenceDetailsDto.setAdherancePercentage(adherenceCount.getAdherancePercentage());
				  adherenceDetailsDto.setDispensationDate(adherenceCount.getDispensationDate());
				  adherenceDetailsDtoList.add(adherenceDetailsDto);
		  }
		  if(!adherenceDetailsDtoList.isEmpty()) {
			  adherenceDetailsDtoList.sort((p1, p2) -> p1.getResultId().compareTo(p2.getResultId())); // sort value in ascending order}
			}
		  beneficiaryTestDetailsDto.setAdherenceDetails(adherenceDetailsDtoList); 
		  }
		  
		  TestDetailsHeaderDto testResultHeaderDtoObject = getHeaderDetails(beneficiaryId,facilityId);
		  beneficiaryTestDetailsDto.setTestDetailsHeaderDto(testResultHeaderDtoObject);
		  return beneficiaryTestDetailsDto; 
  
	
	}

	private TestDetailsHeaderDto getHeaderDetails(Long beneficiaryId, Long facilityId) {

		TestDetailsHeaderDto testDetailsHeaderDto = new TestDetailsHeaderDto();
		Date artStartDate = testDetailsGraphRepository.getStartDate(beneficiaryId, facilityId);
		testDetailsHeaderDto.setArtStartDate(artStartDate);
		
		List<Date> currentAndpreviousDates = testDetailsGraphRepository.getDates(beneficiaryId, facilityId);
		if (!currentAndpreviousDates.equals(null) && !currentAndpreviousDates.isEmpty()) {
			if (currentAndpreviousDates.size() == 2) {
	
					List<TestDetailsGraphBodyProjection> currentVlCountObj = testDetailsGraphRepository.getCurrentVlCount(beneficiaryId, facilityId,currentAndpreviousDates.get(0));
					List<TestDetailsGraphBodyProjection>  previousVlCountObj = testDetailsGraphRepository.getCurrentVlCount(beneficiaryId, facilityId,currentAndpreviousDates.get(1));
	
					if(!currentVlCountObj.isEmpty() && !previousVlCountObj.isEmpty()) {
					  TestDetailsBodyDto testDetailsBodyDtoForCurrentVlCountObj = new TestDetailsBodyDto();
					  testDetailsBodyDtoForCurrentVlCountObj.setResultId(currentVlCountObj.get(0).getResultId());
					  testDetailsBodyDtoForCurrentVlCountObj.setResultType(currentVlCountObj.get(0).getResultType());
					  testDetailsBodyDtoForCurrentVlCountObj.setResultValue(getScalevalue(currentVlCountObj.get(0).getResultType(), currentVlCountObj.get(0).getResultValue()));
					  testDetailsBodyDtoForCurrentVlCountObj.setResultDate(currentVlCountObj.get(0).getResultDate());
					  testDetailsHeaderDto.setCurrentVlCount(testDetailsBodyDtoForCurrentVlCountObj);
				
				    TestDetailsBodyDto testDetailsBodyDtoForPreviousVlCountObj = new TestDetailsBodyDto();
					testDetailsBodyDtoForPreviousVlCountObj.setResultId(previousVlCountObj.get(0).getResultId());
					testDetailsBodyDtoForPreviousVlCountObj.setResultType(previousVlCountObj.get(0).getResultType());
					testDetailsBodyDtoForPreviousVlCountObj.setResultValue(getScalevalue(previousVlCountObj.get(0).getResultType(), previousVlCountObj.get(0).getResultValue()));
					testDetailsBodyDtoForPreviousVlCountObj.setResultDate(previousVlCountObj.get(0).getResultDate());
				
					if(!testDetailsBodyDtoForCurrentVlCountObj.getResultValue().isEmpty() && !testDetailsBodyDtoForPreviousVlCountObj.getResultValue().isEmpty() && !testDetailsBodyDtoForCurrentVlCountObj.getResultValue().equals("0")) {
						Double currentVal = Double.parseDouble(testDetailsBodyDtoForCurrentVlCountObj.getResultValue());
						Double previousVal = Double.parseDouble(testDetailsBodyDtoForPreviousVlCountObj.getResultValue());
						Double percentVal = ((currentVal - previousVal)/currentVal) * 100;
						testDetailsHeaderDto.setChangePercent(percentVal.toString());
					}else {
						testDetailsHeaderDto.setChangePercent(null);
					
					}
					}
			}else if(currentAndpreviousDates.size() == 1) {
				
				List<TestDetailsGraphBodyProjection> currentVlCountObjVal = testDetailsGraphRepository.getCurrentVlCount(beneficiaryId, facilityId,currentAndpreviousDates.get(0));
				 if(!currentVlCountObjVal.isEmpty()) {
				  TestDetailsBodyDto testDetailsBodyDtoForCurrentVlCountObj = new TestDetailsBodyDto();
				  testDetailsBodyDtoForCurrentVlCountObj.setResultId(currentVlCountObjVal.get(0).getResultId());
				  testDetailsBodyDtoForCurrentVlCountObj.setResultType(currentVlCountObjVal.get(0).getResultType());
				  testDetailsBodyDtoForCurrentVlCountObj.setResultValue(getScalevalue(currentVlCountObjVal.get(0).getResultType(), currentVlCountObjVal.get(0).getResultValue()));
				  testDetailsBodyDtoForCurrentVlCountObj.setResultDate(currentVlCountObjVal.get(0).getResultDate());
				  testDetailsHeaderDto.setCurrentVlCount(testDetailsBodyDtoForCurrentVlCountObj);
				  testDetailsHeaderDto.setChangePercent(null);
				 }  
			}
		}
			return testDetailsHeaderDto;
	}
	
	public static String getScalevalue(String key, String value) {

		try {
			String tempVLValue = VlResultTypeConstatnts.VL_VALUES;
			Gson gson = new Gson();
			Type vlListType = new TypeToken<ArrayList<VlValueConstantsForLabDto>>() {}.getType();
			List<VlValueConstantsForLabDto> founderList = gson.fromJson(tempVLValue, vlListType);
			Optional<VlValueConstantsForLabDto> vlObj = founderList.stream()
					.filter(vlObjs -> vlObjs.getName().equalsIgnoreCase(key)).findFirst();
			VlValueConstantsForLabDto constantsDto = vlObj != null && vlObj.isPresent() ? vlObj.get() : null;
			if (constantsDto != null) {
				value = constantsDto.getValue();
			}
		} catch (JsonSyntaxException e) {
			return value;
		}
		return value;
	}


}
