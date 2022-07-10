package com.dislinkt.company.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CompanyRating {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @ManyToOne
    private Company company;

    private String comment;
    private Integer points;
    private Double salary;
    private String selectionProcess;
}
