package com.dislinkt.company.service;

import com.dislinkt.company.model.Company;
import com.dislinkt.company.model.CompanyRegistrationRequest;
import com.dislinkt.company.repository.CompanyRegistrationRequestRepository;
import com.dislinkt.company.repository.CompanyRepository;
import com.dislinkt.exception.RequestNotFoundException;
import com.dislinkt.exception.UserNotFoundException;
import com.dislinkt.user.model.User;
import com.dislinkt.user.repository.UserRepository;
import com.dislinkt.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyRegistrationRequestService {

    private final CompanyRegistrationRequestRepository companyRegistrationRequestRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public List<CompanyRegistrationRequest> getAll() {
        return companyRegistrationRequestRepository.findAll();
    }

    @Transactional(rollbackFor = Throwable.class)
    public void saveRequest(CompanyRegistrationRequest request) {
        companyRegistrationRequestRepository.save(request);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void approveRequest(UUID requestId) {
        CompanyRegistrationRequest request = companyRegistrationRequestRepository.findById(requestId)
                .orElseThrow(RequestNotFoundException::new);
        User owner = userRepository.findByEmail(request.getOwnerEmail())
                .orElseThrow(UserNotFoundException::new);

        companyRepository.save(Company.builder()
                .name(request.getName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .website(request.getWebsite())
                .description(request.getDescription())
                .owner(owner)
                .build());
        owner.setRole(roleService.getRole("ROLE_COMPANY_OWNER"));
        companyRegistrationRequestRepository.deleteById(requestId);
    }
}
