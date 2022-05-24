package com.dislinkt.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@ToString
public class SignUpRequest {

    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
