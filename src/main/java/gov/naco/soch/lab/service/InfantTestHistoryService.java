package gov.naco.soch.lab.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.Beneficiary;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.MasterBatchStatus;
import gov.naco.soch.entity.MasterSampleStatus;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.InfantTestHistoryDto;
import gov.naco.soch.lab.dto.TestHistoryDto;
import gov.naco.soch.lab.util.LabServiceUtil;
import gov.naco.soch.repository.BeneficiaryRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterBatchStatusRepository;
import gov.naco.soch.repository.MasterSampleStatusRepository;

@Service
public class InfantTestHistoryService {

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private MasterSampleStatusRepository masterSampleStatusRepository;

	@Autowired
	private MasterBatchStatusRepository masterBatchStatusRepository;

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	public InfantTestHistoryDto fetchInfantTestHistory(Long infantId, Long labId) {

		Optional<Beneficiary> beneficiaryOpt = beneficiaryRepository.findById(infantId);

		if (beneficiaryOpt.isPresent()) {

			Beneficiary infant = beneficiaryOpt.get();
			
			MasterBatchStatus masterBatchStatus = masterBatchStatusRepository.findByStatusAndIsDelete("DISPATCHED",
					Boolean.FALSE);

			MasterSampleStatus masterSampleStatus = masterSampleStatusRepository.findByStatusAndIsDelete("ACCEPT",
					Boolean.FALSE);

			Predicate<LabTestSample> checkBatchStatus = s -> s.getLabTestSampleBatch().getMasterBatchStatus()
					.getId() != masterBatchStatus.getId();

			Predicate<LabTestSample> isSampleInLab = s -> s.getLabTestSampleBatch().getLab().getId() == labId;

			Predicate<LabTestSample> statusAccepted = s -> s.getMasterSampleStatus().getId() == masterSampleStatus
					.getId();

			List<LabTestSample> labTestSampleList = labTestSampleRepository.findByIsDelete(Boolean.FALSE);

			if (!CollectionUtils.isEmpty(labTestSampleList)) {

				labTestSampleList = labTestSampleList.stream().filter(s -> s.getBeneficiary().getId() == infant.getId())
						.collect(Collectors.toList());
				labTestSampleList = labTestSampleList.stream().filter(checkBatchStatus).collect(Collectors.toList());
				labTestSampleList = labTestSampleList.stream().filter(isSampleInLab.and(statusAccepted))
						.collect(Collectors.toList());
			} else {

			}

			List<TestHistoryDto> testHistoryDtoList = labTestSampleList.stream().map(s -> {

				TestHistoryDto testHistoryDto = new TestHistoryDto();
				testHistoryDto.setVisitDate(s.getSampleCollectedDate().toLocalDate());
//				testHistoryDto.setFeedingType(feedingType);
				testHistoryDto.setAgeOnTest(infant.getAge());
				testHistoryDto.setTestType(s.getTestType().getTestType());
				testHistoryDto.setResult(s.getResultType().getResultType());
				testHistoryDto.setResultDate(s.getResultReceivedDate().toLocalDate());
				testHistoryDto.setResultStatus(s.getMasterResultStatus().getStatus());
				testHistoryDto.setLabName(s.getLabTestSampleBatch().getFacility().getName());

				return testHistoryDto;
			}).collect(Collectors.toList());

			
			InfantTestHistoryDto infantTestHistoryDto = new InfantTestHistoryDto();
			infantTestHistoryDto.setBeneficiaryId(infantId);
			infantTestHistoryDto.setBeneficiaryUid(infant.getUid());
			infantTestHistoryDto.setBeneficiaryName(LabServiceUtil.getBeneficiaryName(infant));
//			infantTestHistoryDto.setMotherId(motherId);
//			infantTestHistoryDto.setMotherName(motherName);
//			infantTestHistoryDto.setMotherUid(motherUid);

			infantTestHistoryDto.setTestHistoryDetails(testHistoryDtoList);

			return infantTestHistoryDto;

		} else {
			throw new ServiceException("Invalid infant Id", null, HttpStatus.BAD_REQUEST);
		}

	}
}
