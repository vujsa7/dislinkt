package com.dislinkt.connectionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ConnectionsDto {
    private List<String> ids;
}