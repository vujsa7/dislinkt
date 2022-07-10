package com.dislinkt.company.repository;

import com.dislinkt.company.model.CompanyRegistrationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CompanyRegistrationRequestRepository extends JpaRepository<CompanyRegistrationRequest, UUID> {
}
