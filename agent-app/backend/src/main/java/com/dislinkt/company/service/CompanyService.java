package com.dislinkt.company.service;

import com.dislinkt.company.model.Company;
import com.dislinkt.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

}
