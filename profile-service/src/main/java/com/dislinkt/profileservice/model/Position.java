package com.dislinkt.profileservice.model;

import lombok.Data;

import java.util.Date;
import java.util.Objects;

@Data
public class Position {
    private String title;
    private EmploymentType employmentType;
    private String companyName;
    private Date startDate;
    private Date endDate;
    private boolean currentPosition;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(title, position.title) && Objects.equals(companyName, position.companyName) && startDate.compareTo(position.startDate)==0
                && endDate.compareTo(position.endDate)==0 && currentPosition==position.currentPosition && employmentType==position.employmentType;
    }
}
