package com.dislinkt.profileservice.model;

import lombok.Data;

import java.util.Date;

@Data
public class Education {
    private String school;
    private String degree;
    private String fieldOfStudy;
    private Date startDate;
    private Date endDate;
}
