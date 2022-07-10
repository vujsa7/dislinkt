package com.dislinkt.company.dto;

import com.dislinkt.company.model.Company;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@AllArgsConstructor
@ToString
public class CompanyRatingRequest {

    private String comment;
    private Integer points;
    private Double salary;
    private String selectionProcess;
}
