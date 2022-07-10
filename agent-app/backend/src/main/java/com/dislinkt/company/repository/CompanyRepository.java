package com.dislinkt.company.repository;

import com.dislinkt.company.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    List<Company> findAllByOwnerEmail(String ownerEmail);
}
