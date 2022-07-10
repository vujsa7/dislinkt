package com.dislinkt.company.service;

import com.dislinkt.company.model.CompanyRating;
import com.dislinkt.company.repository.CompanyRatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyRatingService {

    private final CompanyRatingRepository companyRatingRepository;

    @Transactional(rollbackFor = Throwable.class)
    public void saveRating(CompanyRating rating) {
        companyRatingRepository.save(rating);
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<CompanyRating> getAll(UUID companyId) {
        return companyRatingRepository.findAllByCompanyId(companyId);
    }
}
