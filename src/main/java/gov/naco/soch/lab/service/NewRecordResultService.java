package gov.naco.soch.lab.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.naco.soch.lab.dto.NewRecordResultDto;
import gov.naco.soch.repository.TestDetailsGraphRepository;

@Service
@Transactional
public class NewRecordResultService {

//    private final TestDetailsGraphRepository testDetailsGraphRepository;
//
//    @Autowired
//    public NewRecordResultService(TestDetailsGraphRepository testDetailsGraphRepository) {
//        this.testDetailsGraphRepository = testDetailsGraphRepository;
//    }
//
//    public Page<NewRecordResultDto> getRecordResultsList(Long labId, int pageNo, int pageSize) {
//        Pageable paging = PageRequest.of(pageNo, pageSize);
//        return testDetailsGraphRepository.findSamplesToRecordResult(labId, paging);
//    }
}
