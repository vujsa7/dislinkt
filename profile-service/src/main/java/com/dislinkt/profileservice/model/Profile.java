package com.dislinkt.profileservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Profile {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Gender gender;
    private Date dateOfBirth;
    private String biography;
    private List<Position> experience;
    private List<Education> education;
    private List<String> skills;
    private List<String> interests;
    private ProfileType profileType;

    public Profile(String id, String username, String firstName, String lastName, String email, String phoneNumber, Gender gender, Date dateOfBirth, String biography, ProfileType profileType) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.profileType = profileType;
    }

    public Profile(String username, String firstName, String lastName, String email, String phoneNumber, Gender gender,
                   Date dateOfBirth, String biography, List<Position> experience, List<Education> education,
                   List<String> skills, List<String> interests, ProfileType profileType) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.biography = biography;
        this.experience = experience;
        this.education = education;
        this.skills = skills;
        this.interests = interests;
        this.profileType = profileType;
    }

    public boolean isPrivate(){
        return this.profileType.equals(ProfileType.PRIVATE);
    }
}
