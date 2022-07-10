package com.dislinkt.company.service;

import com.dislinkt.company.model.Company;
import com.dislinkt.company.repository.CompanyRepository;
import com.dislinkt.exception.CompanyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<Company> getAll(String ownerEmail) {
        return companyRepository.findAllByOwnerEmail(ownerEmail);
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public Company getById(UUID id) {
        return companyRepository.findById(id).orElseThrow(CompanyNotFoundException::new);
    }
}
