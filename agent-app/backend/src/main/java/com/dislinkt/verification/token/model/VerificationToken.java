package com.dislinkt.verification.token.model;

import com.dislinkt.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VerificationToken {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private UUID token;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    private Date expirationDate;

    private boolean used;
}
