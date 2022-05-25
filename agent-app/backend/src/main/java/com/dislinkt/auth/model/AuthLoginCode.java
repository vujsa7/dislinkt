package com.dislinkt.auth.model;

import com.dislinkt.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthLoginCode {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private Date expirationDate;

    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
