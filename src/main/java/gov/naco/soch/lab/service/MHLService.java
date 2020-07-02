package gov.naco.soch.lab.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

@Service
@Transactional
public class MHLService {

	@Value("${mhl.auth_key}")
	private String authKey;

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private FacilityRepository facilityRepository;

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
					for (LocalDate date : patientLoadDto.getLoad_date()) {
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
			}
			else {
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
					} else {
						switch (recordResultDto.getResult_value()) {
						case "target not detected":
						case "tnd": {
							resultTypeId = 12L;
							resultStatusId = 3L;
							sampleStatusId = 4L;
						}
							break;
						case "<20": {
							resultTypeId = 11L;
							resultStatusId = 3L;
							sampleStatusId = 4L;
							break;
						}
						case ">10000000": {
							resultTypeId = 13L;
							resultStatusId = 3L;
							sampleStatusId = 4L;
							break;
						}
						case "quantity not sufficient":
						case "quantity not sufficient for testing":
						case "qns": {
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 4L;
							break;
						}
						case "sample haemolysed":
						case "sample grossly haemolysed":
						case "haemolysed": {
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 1L;
							break;
						}
						case "fibrin clot":
						case "clotted sample":
						case "sample clotted": {
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 11L;
							break;
						}
						case "labelling issue": {
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 6L;
							break;
						}
						case "qs invalid": {
							resultTypeId = 15L;
							resultStatusId = 5L;
							sampleStatusId = 2L;
							remarkId = 12L;
							break;
						}

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
						labTestSampleRepository.save(labTestSample);
						responseDto.setCode(200);
						responseDto.setMsg("Success");
						responseDto.setSuccess(true);
					} catch (Exception e) {
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

}
