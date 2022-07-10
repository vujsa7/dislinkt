package com.dislinkt.company.dto;

import com.dislinkt.company.model.CompanyRating;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class CompanyRatingsResponse {

    private List<CompanyRating> ratings;
}
