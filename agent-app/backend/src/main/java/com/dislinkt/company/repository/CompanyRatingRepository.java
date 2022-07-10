package com.dislinkt.company.repository;

import com.dislinkt.company.model.CompanyRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRatingRepository extends JpaRepository<CompanyRating, UUID> {

    List<CompanyRating> findAllByCompanyId(UUID companyId);
}
