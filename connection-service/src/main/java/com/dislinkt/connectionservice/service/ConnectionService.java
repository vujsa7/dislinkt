package com.dislinkt.connectionservice.service;

import com.dislinkt.connectionservice.model.ConnectionStatus;

import java.util.List;

public interface ConnectionService {
    ConnectionStatus modifyConnection(String id, String followerId, Boolean isFollowerPrivate);
    List<String> getAllConnectionsForUser(String id);
    List<String> getAllFollowRequestsForUser(String id);
}
