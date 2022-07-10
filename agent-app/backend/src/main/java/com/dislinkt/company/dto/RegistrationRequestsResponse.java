package com.dislinkt.company.dto;

import com.dislinkt.company.model.CompanyRegistrationRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class RegistrationRequestsResponse {

    private List<CompanyRegistrationRequest> requests;
}
