package com.dislinkt.company.controller;

import com.dislinkt.company.dto.CompaniesResponse;
import com.dislinkt.company.dto.RegistrationRequest;
import com.dislinkt.company.dto.RegistrationRequestsResponse;
import com.dislinkt.company.model.CompanyRegistrationRequest;
import com.dislinkt.company.service.CompanyRegistrationRequestService;
import com.dislinkt.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRegistrationRequestService companyRegistrationRequestService;

    @PostMapping("/company-registration-requests")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> saveRequest(@Valid @RequestBody RegistrationRequest request, Principal principal) {
        companyRegistrationRequestService.saveRequest(CompanyRegistrationRequest.builder()
                .name(request.getCompanyName())
                .address(request.getCompanyAddress())
                .phoneNumber(request.getCompanyPhoneNumber())
                .website(request.getCompanyWebsite())
                .description(request.getCompanyDescription())
                .ownerEmail(principal.getName())
                .build());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/company-registration-requests")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<RegistrationRequestsResponse> getRequests() {
        List<CompanyRegistrationRequest> requests = companyRegistrationRequestService.getAll();
        return new ResponseEntity<>(RegistrationRequestsResponse.builder()
                .requests(requests)
                .build(), HttpStatus.OK);
    }

    @PutMapping(value = "/company-registration-requests/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> approveRequest(@PathVariable UUID id) {
        companyRegistrationRequestService.approveRequest(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/companies")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<CompaniesResponse> getAll() {
        return new ResponseEntity<>(CompaniesResponse.builder()
                .companies(companyService.getAll())
                .build(), HttpStatus.OK);
    }

    @GetMapping(value = "/my-companies")
    @PreAuthorize("hasRole('ROLE_COMPANY_OWNER')")
    public ResponseEntity<CompaniesResponse> getMyCompanies(Principal principal) {
        return new ResponseEntity<>(CompaniesResponse.builder()
                .companies(companyService.getAll(principal.getName()))
                .build(), HttpStatus.OK);
    }
}
