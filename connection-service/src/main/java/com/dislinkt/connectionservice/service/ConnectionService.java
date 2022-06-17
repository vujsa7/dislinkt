package com.dislinkt.connectionservice.service;

import com.dislinkt.connectionservice.model.ProfileEntity;

import java.util.List;

public interface ConnectionService {
    boolean modifyConnection(String id, String followerId);
    List<String> getAllConnectionsForUser(String id);
}
