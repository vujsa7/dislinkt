package com.dislinkt.connectionservice.dto;

import lombok.Data;

@Data
public class NewConnectionDto {
    private String followerId;
    private Boolean isFollowerPrivate;
}
