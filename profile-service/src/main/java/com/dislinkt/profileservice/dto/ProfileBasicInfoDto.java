package com.dislinkt.profileservice.dto;

import com.dislinkt.profileservice.model.Gender;
import com.dislinkt.profileservice.model.ProfileType;
import lombok.Data;

import java.util.Date;

@Data
public class ProfileBasicInfoDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Date dateOfBirth;
    private String biography;
    private ProfileType profileType;
}
