package com.dislinkt.profileservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class Position {
    private String title;
    private EmploymentType employmentType;
    private String companyName;
    private Date startDate;
    private Date endDate;
    private boolean currentPosition;
}
