package com.dislinkt.connectionservice.service;

import com.dislinkt.connectionservice.model.ConnectionStatus;

import java.util.List;

public interface ConnectionService {
    ConnectionStatus modifyConnection(String id, String followerId, Boolean isFollowerPrivate);
    List<String> getFollowing(String id);
    List<String> getFollowingRequests(String id);
    void acceptFollowRequest(String name, String id);
    void deleteFollowRequest(String principalId, String id);
    List<String> getFollowers(String name);
    void deleteFollowing(String name, String id);
    void deleteFollower(String name, String id);
}
