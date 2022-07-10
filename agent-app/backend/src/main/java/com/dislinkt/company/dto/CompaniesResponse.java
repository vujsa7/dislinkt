package com.dislinkt.company.dto;

import com.dislinkt.company.model.Company;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class CompaniesResponse {

    private List<Company> companies;
}
