package com.dislinkt.postservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ConnectionsDto {
    private List<String> ids;

    public void addId(String id) {
        this.ids.add(id);
    }
}