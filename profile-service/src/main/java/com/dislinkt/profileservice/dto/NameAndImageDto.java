package com.dislinkt.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class NameAndImageDto {
    private String id;
    private String fullName;
    private String image;
}
