package gov.naco.soch.lab.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import gov.naco.soch.entity.Beneficiary;
import gov.naco.soch.entity.BeneficiaryFamilyDetail;
import gov.naco.soch.entity.IctcSampleCollection;
import gov.naco.soch.entity.LabTestSample;
import gov.naco.soch.entity.MasterInfantBreastFeed;
import gov.naco.soch.exception.ServiceException;
import gov.naco.soch.lab.dto.InfantTestHistoryDto;
import gov.naco.soch.lab.dto.TestHistoryDto;
import gov.naco.soch.lab.util.LabServiceUtil;
import gov.naco.soch.repository.BeneficiaryFamilyDetailRepository;
import gov.naco.soch.repository.BeneficiaryRepository;
import gov.naco.soch.repository.IctcSampleCollectionRepository;
import gov.naco.soch.repository.LabTestSampleRepository;
import gov.naco.soch.repository.MasterInfantBreastFeedRepository;

@Service
@Transactional
public class InfantTestHistoryService {

	@Autowired
	private LabTestSampleRepository labTestSampleRepository;

	@Autowired
	private BeneficiaryRepository beneficiaryRepository;

	@Autowired
	private IctcSampleCollectionRepository ictcSampleCollectionRepository;

	@Autowired
	private BeneficiaryFamilyDetailRepository beneficiaryFamilyDetailRepository;

	@Autowired
	private MasterInfantBreastFeedRepository masterInfantBreastFeedRepository;

	public InfantTestHistoryDto fetchInfantTestHistory(Long infantId, Long labId) {

		Optional<Beneficiary> beneficiaryOpt = beneficiaryRepository.findById(infantId);

		if (beneficiaryOpt.isPresent()) {

			Beneficiary infant = beneficiaryOpt.get();

			List<LabTestSample> labTestSampleList = labTestSampleRepository.findSamplesByBeneficiaryIdAndLabId(infantId,
					labId);

			List<TestHistoryDto> testHistoryDtoList = labTestSampleList.stream().map(s -> {

				TestHistoryDto testHistoryDto = new TestHistoryDto();
//				testHistoryDto.setVisitDate(s.getSampleCollectedDate().toLocalDate());
//				testHistoryDto.setFeedingType(feedingType);
				testHistoryDto.setAgeOnTest(infant.getAge());
				testHistoryDto.setBeneficiaryDob(infant.getDateOfBirth());
				testHistoryDto.setTestType(s.getTestType().getTestType());
				if (s.getResultType() != null) {
					testHistoryDto.setResult(s.getResultType().getResultType());
				}
				if (s.getResultReceivedDate() != null) {
					testHistoryDto.setResultDate(s.getResultReceivedDate().toLocalDate());
				}
				testHistoryDto.setResultStatus(s.getMasterResultStatus().getStatus());
				testHistoryDto.setLabName(s.getLabTestSampleBatch().getFacility().getName());
				testHistoryDto.setBarcodeNumber(s.getBarcodeNumber());
				return testHistoryDto;
			}).collect(Collectors.toList());

			if (!CollectionUtils.isEmpty(testHistoryDtoList)) {
				fetchIctcInfantDetails(testHistoryDtoList);
			}

			InfantTestHistoryDto infantTestHistoryDto = new InfantTestHistoryDto();
			infantTestHistoryDto.setBeneficiaryId(infantId);
			infantTestHistoryDto.setBeneficiaryUid(infant.getUid());
			infantTestHistoryDto.setBeneficiaryName(LabServiceUtil.getBeneficiaryName(infant));

			Optional<BeneficiaryFamilyDetail> motherDetilsOpt = beneficiaryFamilyDetailRepository
					.findByBeneficiaryIdAndRelationshipId(infantId, 4L);
			if (motherDetilsOpt.isPresent()) {
				Beneficiary motherDetils = motherDetilsOpt.get().getPartnerBeneficiary();
				infantTestHistoryDto.setMotherId(motherDetils.getId());
				infantTestHistoryDto.setMotherName(LabServiceUtil.getBeneficiaryName(motherDetils));
				infantTestHistoryDto.setMotherUid(motherDetils.getUid());
			}

			infantTestHistoryDto.setTestHistoryDetails(testHistoryDtoList);

			return infantTestHistoryDto;

		} else {
			throw new ServiceException("Invalid infant Id", null, HttpStatus.BAD_REQUEST);
		}

	}

	private void fetchIctcInfantDetails(List<TestHistoryDto> testResultDto) {

		List<String> barcodes = testResultDto.stream().map(s -> s.getBarcodeNumber()).collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(barcodes)) {

			List<IctcSampleCollection> ictcSamples = ictcSampleCollectionRepository.findBySampleBatchBarcodes(barcodes);
			if (!CollectionUtils.isEmpty(ictcSamples)) {

				List<MasterInfantBreastFeed> infantBreastStatus = masterInfantBreastFeedRepository
						.findByIsDelete(Boolean.FALSE);

				Map<Long, String> infantBreastStatusMap = infantBreastStatus.stream()
						.collect(Collectors.toMap(MasterInfantBreastFeed::getId, MasterInfantBreastFeed::getName));

				Map<String, IctcSampleCollection> ictcBenificiaryDetailsMap = new HashMap<>();
				for (IctcSampleCollection s : ictcSamples) {
					ictcBenificiaryDetailsMap.put(s.getBarcode(), s);
				}

				testResultDto.stream().forEach(s -> {

					IctcSampleCollection ictcBenificiaryDetails = ictcBenificiaryDetailsMap.get(s.getBarcodeNumber());
					if (ictcBenificiaryDetails != null) {
						if (ictcBenificiaryDetails.getVisit() != null) {
							s.setFeedingType(
									infantBreastStatusMap.get(ictcBenificiaryDetails.getVisit().getInfantBreastFed()));
							s.setVisitDate(ictcBenificiaryDetails.getVisit().getVisitDate());
						}
					}
				});
			}
		}
	}
}
