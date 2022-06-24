package com.dislinkt.profileservice.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchedProfileDto {
    private String id;
    private String username;
    private String firstName;
    private String lastName;
}
