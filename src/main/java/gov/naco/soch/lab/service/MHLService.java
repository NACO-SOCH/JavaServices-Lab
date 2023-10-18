package gov.naco.soch.lab.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.Facility;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.LabTestSampleBatch;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterRemark;
import gov.naco.soch.entity.MasterResultStatus;
import gov.naco.soch.entity.MasterResultType;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.lab.dto.ClientDetailsDto;
import gov.naco.soch.lab.dto.MHLBarcodeValidationDto;
import gov.naco.soch.lab.dto.MHLSampleDetailDto;
import gov.naco.soch.lab.dto.PatientLoadDto;
import gov.naco.soch.lab.dto.PatientLoadResponseDto;
import gov.naco.soch.lab.dto.RecordResultDto;
import gov.naco.soch.lab.dto.ResponseDto;
import gov.naco.soch.lab.dto.ResultDto;
import gov.naco.soch.lab.mapper.MHLMapper;
import gov.naco.soch.repository.FacilityRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;

@Service
@Transactional
public class MHLService {

	private static String RECIEVED = "RECIEVED";

	private static String REJECTED = "REJECTED";

	private static String PARTIALLY_RECEIVED = "PARTIALLY RECEIVED";

	private static String RESULT_POSTED = "RESULT POSTED";

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	@Value("${mhl.auth_key}")
	private String authKey;

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private FacilityRepository facilityRepository;

	@Autowired
	private MasterBatchStatusRepository masterBatchStatusRepository;

	public ResponseEntity<Object> getViralLoadSampleDetail(@Valid MHLBarcodeValidationDto mhlBarcodeValidationDto) {
		System.out.println(mhlBarcodeValidationDto.getAuth_key());
		if (mhlBarcodeValidationDto.getAuth_key() != null) {
			if (authKey.equals(mhlBarcodeValidationDto.getAuth_key())) {
				ResultDto resultDto = new ResultDto();
				Optional<LabTestSample> labTestSampleOptional = labTestSampleRepository
						.findByBarcodeNumber(mhlBarcodeValidationDto.getBarcode());
				if (labTestSampleOptional.isPresent()) {
					LabTestSample labTestSample = labTestSampleOptional.get();
					resultDto = MHLMapper.mapLabTestSampleToResponseDto(labTestSample);
					return responseManager(resultDto, "Success", false);
				} else {
					return responseManager(null, "Invalid data!", false); 
				}
			} else {
				return responseManager(null, "Authentication failed!", true);
			}
		} else {
			return responseManager(null, "Authentication failed!", true);
		}
	}

	public ResponseEntity<Object> responseManager(ResultDto resultDto, String msg, boolean authfailed) {
		MHLSampleDetailDto property = new MHLSampleDetailDto();
		if (authfailed) {
			property.setCode(HttpStatus.UNAUTHORIZED.value());
			property.setMsg(msg);
			property.setSuccess(false);
			return new ResponseEntity<Object>(property, HttpStatus.UNAUTHORIZED);
		}

		if (resultDto != null) {
			property.setCode(HttpStatus.OK.value());
			property.setMsg(msg);
			property.setSuccess(true);
			property.setResult(resultDto);
			return new ResponseEntity<Object>(property, HttpStatus.OK);
		} else {
			property.setCode(HttpStatus.BAD_REQUEST.value());
			property.setMsg(msg);
			property.setSuccess(false);
			return new ResponseEntity<Object>(property, HttpStatus.BAD_REQUEST);
		}

	}

	public ResponseEntity<Object> getVLPatientLoad(@Valid PatientLoadDto patientLoadDto) {
		if (patientLoadDto.getAuth_key() != null) {
			if (authKey.contentEquals(patientLoadDto.getAuth_key())) {
				PatientLoadResponseDto patientLoadResponseDto = new PatientLoadResponseDto();
				List<ClientDetailsDto> clientDetailsDtos = new ArrayList<>();
				if (!CollectionUtils.isEmpty(patientLoadDto.getLoad_date())) {
					for (String stringDate : patientLoadDto.getLoad_date()) {

						LocalDate date = LocalDate.parse(stringDate, formatter);

						for (String facilityCode : patientLoadDto.getClient_code()) {
							Optional<Facility> facilityOptional = facilityRepository.findByCode(facilityCode);
							if (facilityOptional.isPresent()) {
								List<ClientDetailsDto> clientDetails = new ArrayList<>();
								clientDetails = findclientDetails(date, facilityOptional);
								clientDetailsDtos.addAll(clientDetails);
							}
						}
					}
				} else {
					for (String facilityCode : patientLoadDto.getClient_code()) {
						Optional<Facility> facilityOptional = facilityRepository.findByCode(facilityCode);
						if (facilityOptional.isPresent()) {
							clientDetailsDtos = findclientDetails(LocalDate.now(), facilityOptional);
						}
					}
				}
				patientLoadResponseDto.setCode(200);
				patientLoadResponseDto.setMsg("Success");
				patientLoadResponseDto.setSuccess(true);
				patientLoadResponseDto.setResult(clientDetailsDtos);
				return new ResponseEntity<Object>(patientLoadResponseDto, HttpStatus.OK);
			} else {
				return responseManager(null, "Authentication failed!", true);
			}
		} else {
			return responseManager(null, "Authentication failed!", true);
		}

	}

	private List<ClientDetailsDto> findclientDetails(LocalDate date, Optional<Facility> facilityOptional) {
		List<ClientDetailsDto> clientDetailsDtos = new ArrayList<>();
		if (facilityOptional.isPresent()) {
			Facility facility = facilityOptional.get();
			Integer count = labTestSampleRepository.findBeneficiaryLoadByDate(facility.getId(), date);
			if (count != null) {
				ClientDetailsDto clientDetailsDto = new ClientDetailsDto();
				clientDetailsDto.setCLIENT_CODE(facility.getCode());
				clientDetailsDto.setCLIENT_NAME(facility.getName());
				clientDetailsDto.setDATE(date);
				if (facility.getAddress() != null) {
					if (facility.getAddress().getDistrict() != null) {
						clientDetailsDto.setDISTRICT(facility.getAddress().getDistrict().getName());
					} else if (facility.getAddress().getState() != null) {
						clientDetailsDto.setSTATE(facility.getAddress().getState().getName());
					}
				}
				clientDetailsDto.setPATIENT_LOAD(count);
				clientDetailsDto.setCode(200);
				clientDetailsDto.setSuccess(true);
				clientDetailsDto.setMsg("Success");
				clientDetailsDtos.add(clientDetailsDto);
			}
		}
		return clientDetailsDtos;
	}

	public ResponseEntity<Object> recordResult(@Valid RecordResultDto recordResultDto) {

		if (recordResultDto.getAuth_key() != null) {
			if (authKey.contentEquals(recordResultDto.getAuth_key())) {
				MasterResultType resultType = new MasterResultType();
				MasterResultStatus masterResultStatus = new MasterResultStatus();
				MasterSampleStatus masterSampleStatus = new MasterSampleStatus();
				MasterRemark masterRemark = new MasterRemark();
				ResponseDto responseDto = new ResponseDto();
				Optional<LabTestSample> labTestSampleOptional = labTestSampleRepository
						.findByBarcodeNumber(recordResultDto.getBarcode());
				if (labTestSampleOptional.isPresent()) {
					LabTestSample labTestSample = labTestSampleOptional.get();
					Double num = 0.0;
					Long resultTypeId = 0L;
					Long resultStatusId = 0L;
					Long sampleStatusId = 0L;
					Long remarkId = 0L;
					String status = "";
					boolean numeric = true;
					try {
						num = Double.parseDouble(recordResultDto.getResult_value());
					} catch (NumberFormatException e) {
						numeric = false;
					}

					if (numeric) {
						labTestSample.setResultValue(num.toString());
						Double logValue = Math.log(num);
						if (!logValue.isNaN()) {
							labTestSample.setLogValue(logValue.toString());
						}
						resultTypeId = 14L;
						resultStatusId = 3L;
						sampleStatusId = 4L;
						status = RESULT_POSTED;
					} else {
						switch (recordResultDto.getResult_value()) {
						case "target not detected":
						case "tnd":
							resultTypeId = 12L;
							resultStatusId = 3L;
							sampleStatusId = 4L;
							status = RESULT_POSTED;
							break;

						case "<20":
							resultTypeId = 11L;
							resultStatusId = 3L;
							sampleStatusId = 4L;
							status = RESULT_POSTED;
							break;

						case ">10000000":
							resultTypeId = 13L;
							resultStatusId = 3L;
							sampleStatusId = 4L;
							status = RESULT_POSTED;
							break;

						case "quantity not sufficient":
						case "quantity not sufficient for testing":
						case "qns":
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 4L;
							status = REJECTED;
							break;

						case "sample haemolysed":
						case "sample grossly haemolysed":
						case "haemolysed":
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 1L;
							status = REJECTED;
							break;

						case "fibrin clot":
						case "clotted sample":
						case "sample clotted":
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 11L;
							status = REJECTED;
							break;

						case "labelling issue":
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 6L;
							status = REJECTED;
							break;

						case "qs invalid":
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 12L;
							status = REJECTED;
							break;

						default:
							break;
						}

					}
					if (resultTypeId != 0L) {
						resultType.setId(resultTypeId);
						labTestSample.setResultType(resultType);
					}
					if (resultStatusId != 0L) {
						masterResultStatus.setId(resultStatusId);
						labTestSample.setMasterResultStatus(masterResultStatus);
					}
					if (sampleStatusId != 0L) {
						masterSampleStatus.setId(sampleStatusId);
						labTestSample.setMasterSampleStatus(masterSampleStatus);
					}
					if (remarkId != 0L) {
						masterRemark.setId(remarkId);
						labTestSample.setMasterRemark(masterRemark);
					}
					try {
						labTestSample.setArtcSampleStatus(status);
						LocalDateTime currentTime = LocalDateTime.now();
						labTestSample.setSampleReceivedDate(currentTime);
						labTestSample.setResultApprovedDate(currentTime);
						labTestSample.setResultDispatchDate(currentTime);
						labTestSample.setResultReceivedDate(currentTime);
						changeBatchStatus(labTestSample);
						labTestSampleRepository.save(labTestSample);
						responseDto.setCode(200);
						responseDto.setMsg("Success");
						responseDto.setSuccess(true);
					} catch (Exception e) {
						e.printStackTrace();
						responseDto.setCode(400);
						responseDto.setMsg("Invalid data!");
						responseDto.setSuccess(false);
					}
				} else {
					responseDto.setCode(400);
					responseDto.setMsg("Invalid data!");
					responseDto.setSuccess(false);
				}
				return new ResponseEntity<Object>(responseDto, HttpStatus.OK);
			} else {
				return responseManager(null, "Authentication failed!", true);
			}
		} else {
			return responseManager(null, "Authentication failed!", true);
		}

	}

	private void changeBatchStatus(LabTestSample labTestSample) {

		LabTestSampleBatch labTestSampleBatchList = labTestSample.getLabTestSampleBatch();
		if (labTestSampleBatchList != null) {

			String batchStatus = findBatchStatus(labTestSampleBatchList.getLabTestSamples());
			MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete(batchStatus,
					Boolean.FALSE);

			if (labTestSampleBatchList.getReceivedDate() == null) {
				labTestSampleBatchList.setReceivedDate(LocalDateTime.now());
			}

			Boolean accepted = Boolean.FALSE;
			int acceptCount = 0;
			if (!CollectionUtils.isEmpty(labTestSampleBatchList.getLabTestSamples())) {
				for (LabTestSample s : labTestSampleBatchList.getLabTestSamples()) {
					if (s.getMasterResultStatus().getId() == 3L) {
						acceptCount++;
					}
				}
				if (acceptCount == labTestSampleBatchList.getLabTestSamples().size()) {
					accepted = Boolean.TRUE;
				}
			}
			if (accepted) {
				labTestSampleBatchList.setMasterBatchStatus(masterBatchStatus);
			}

		}
	}

	private String findBatchStatus(Set<LabTestSample> labTestSamples) {
		int acceptCount = 0;
		int rejectCount = 0;
		int notRecievedCount = 0;
		int samplesCount = labTestSamples.size();
		for (LabTestSample s : labTestSamples) {
			if (s.getMasterSampleStatus().getId() == 1L) {
				acceptCount++;
			}
			if (s.getMasterSampleStatus().getId() == 2L) {
				rejectCount++;
			}
			if (s.getMasterSampleStatus().getId() == 3L) {
				notRecievedCount++;
			}
		}
		if (acceptCount == samplesCount) {
			return RECIEVED;
		}
		if (rejectCount == samplesCount || notRecievedCount == samplesCount) {
			return REJECTED;
		}
		return PARTIALLY_RECEIVED;
	}
<<<<<<< HEAD
	
	public List<Object[]> getLoginCount(Integer stateId, Date startDate, Date endDate, Integer facilityId, Integer facilityTypeId, Integer DistrictId) {
	    if (facilityId == null && facilityTypeId == null && DistrictId == null) {
	        return masterBatchStatusRepository.getLoginCount(stateId, startDate, endDate);
	    } else if (facilityId == null && facilityTypeId != null && DistrictId == null) {
	        return masterBatchStatusRepository.getLoginCountfac(stateId, startDate, endDate, facilityTypeId);
	    } else if (facilityId == null && facilityTypeId == null && DistrictId != null) {
	        return masterBatchStatusRepository.getLoginCountWithDistrict(stateId, startDate, endDate, DistrictId);
	    } else if (facilityId == null && facilityTypeId != null && DistrictId != null) {
	        return masterBatchStatusRepository.getLoginCountFacAndDistrict(stateId, startDate, endDate, facilityTypeId, DistrictId);
	    } else {
	        return masterBatchStatusRepository.getLoginCountallfac(stateId, startDate, endDate, facilityId, facilityTypeId, DistrictId);
	    }
	}

//	public List<Object[]> getFacilities(Long stateId, Long facilityTypeId, Long districtId) {
//		if ( facilityTypeId == null && districtId == null) {
//	        return masterBatchStatusRepository.getFacilitiesbystate(stateId);
//	    }else if(districtId != null && facilityTypeId != null) {
//        return masterBatchStatusRepository.getFacilitiesbyDistrict(stateId, facilityTypeId, districtId);
//	    }else {
//	    	return masterBatchStatusRepository.getFacilities(stateId, facilityTypeId);
//	    }
//	}
	public List<Object[]> getFacilities(Long stateId, Long facilityTypeId, Long DistrictId) {
	    if (DistrictId != null && facilityTypeId != null) {
	        return masterBatchStatusRepository.getFacilitiesByDistrict(stateId, facilityTypeId, DistrictId);
	    } else if (facilityTypeId != null) {
	        return masterBatchStatusRepository.getFacilitiesByStateAndType(stateId, facilityTypeId);
	    } else if (DistrictId != null) {
	        return masterBatchStatusRepository.getFacilitiesByStateAndDistrict(stateId, DistrictId);
	    } else {
	        return masterBatchStatusRepository.getFacilitiesByState(stateId);
	    }
=======
	public List<Object[]>getLoginCount(Integer stateId, Date startDate, Date endDate, Integer facilityId, Integer facilityTypeId, Integer DistrictId) {
		if (facilityId == null && facilityTypeId == null && DistrictId == null) {
			return masterBatchStatusRepository.getLoginCount(stateId, startDate, endDate);
		} else if (facilityId == null && facilityTypeId != null && DistrictId == null) {
			return masterBatchStatusRepository.getLoginCountfac(stateId, startDate, endDate, facilityTypeId);
		} else if (facilityId == null && facilityTypeId == null && DistrictId != null) {
			return masterBatchStatusRepository.getLoginCountWithDistrict(stateId, startDate, endDate, DistrictId);
		} else if (facilityId == null && facilityTypeId != null && DistrictId != null) {
			return masterBatchStatusRepository.getLoginCountFacAndDistrict(stateId, startDate, endDate, facilityTypeId, DistrictId);
		} else {
			return masterBatchStatusRepository.getLoginCountallfac(stateId, startDate, endDate, facilityId, facilityTypeId, DistrictId);
			}
		}

	public List<Object[]>getFacilities(Long stateId, Long facilityTypeId, Long DistrictId) {
		if (DistrictId != null && facilityTypeId != null) {
			return masterBatchStatusRepository.getFacilitiesByDistrict(stateId, facilityTypeId, DistrictId);
	     } else if (facilityTypeId != null) {
	    	 return masterBatchStatusRepository.getFacilitiesByStateAndType(stateId, facilityTypeId);
		 } else if (DistrictId != null) {
			return masterBatchStatusRepository.getFacilitiesByStateAndDistrict(stateId, DistrictId);
		} else {
			return masterBatchStatusRepository.getFacilitiesByState(stateId);
		}
	}
	
	public Stream<Object[]>getLoginReport(Date startDate, Date endDate) {
	   return masterBatchStatusRepository.getLoginReport(startDate, endDate).stream();
		
		}
	public Stream<Object[]>getLoginReportRole(Integer stateId, Date startDate, Date endDate) {
		if (stateId == null) {
			return masterBatchStatusRepository.getLoginReportRoleAll(startDate, endDate).stream();		   			
			}
		else {
			return masterBatchStatusRepository.getLoginReportRole(stateId, startDate, endDate).stream();
		}
	}
	
	public Stream<Object[]>getLoginReportSacs(Date startDate, Date endDate) {
		   return masterBatchStatusRepository.getLoginReportSacs(startDate, endDate).stream();
			
			}
	
	public Stream<Object[]>getFacilityLine(Integer stateId) {
			return masterBatchStatusRepository.getFacilityLine(stateId).stream();
		}
	
	public Stream<Object[]>getMasterLine(Integer stateId) {
		return masterBatchStatusRepository.getMasterLine(stateId).stream();
	}
	
	public Stream<Object[]>getdispensationReport(Integer facilityId, Date startDate, Date endDate) {
		   return masterBatchStatusRepository.getdispensationReport(facilityId, startDate, endDate).stream();			
>>>>>>> d24c73822b38325e281954ce4c24abfee2324ead
	}
}
