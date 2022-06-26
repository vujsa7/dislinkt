package com.dislinkt.profileservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NetworkDto {
    private boolean isPrivate;
    private List<NameAndImageDto> requests;
    private List<NameAndImageDto> following;
    private List<NameAndImageDto> followers;
}
