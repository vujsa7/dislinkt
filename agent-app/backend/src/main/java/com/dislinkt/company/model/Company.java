package com.dislinkt.company.model;

import com.dislinkt.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Company {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @ManyToOne
    private User owner;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String description;

    private String website;
}
