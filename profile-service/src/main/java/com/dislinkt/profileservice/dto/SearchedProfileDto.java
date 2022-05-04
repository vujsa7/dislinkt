package com.dislinkt.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchedProfileDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
}
