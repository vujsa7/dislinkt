package com.dislinkt.company.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@ToString
public class RegistrationRequest {

    @NotBlank
    private String companyName;

    @NotBlank
    private String companyPhoneNumber;

    @NotBlank
    private String companyAddress;

    @NotBlank
    private String companyDescription;

    @NotBlank
    private String companyWebsite;
}
