package com.dislinkt.profileservice.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Education {
    private String school;
    private String degree;
    private String fieldOfStudy;
    private Date startDate;
    private Date endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return Objects.equals(school, education.school) && Objects.equals(degree, education.degree) && Objects.equals(fieldOfStudy, education.fieldOfStudy)
                && startDate.compareTo(education.startDate)==0 && endDate.compareTo(education.endDate)==0;
    }
}
