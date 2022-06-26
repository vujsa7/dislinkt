package com.dislinkt.connectionservice.service.impl;

import com.dislinkt.connectionservice.dao.ConnectionRepository;
import com.dislinkt.connectionservice.model.ConnectionStatus;
import com.dislinkt.connectionservice.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionServiceImpl(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public ConnectionStatus modifyConnection(String id, String followerId, Boolean isFollowerPrivate) {
        ConnectionStatus connectionStatus = null;
        if(connectionRepository.hasRelationship(id, followerId)){
            connectionRepository.deleteFollowingOrFollowingRequest(id, followerId);
            connectionStatus = ConnectionStatus.NO_FOLLOW;
        }
        else{
            if(isFollowerPrivate){
                connectionRepository.createNewFollowingRequest(id, followerId, true);
                connectionStatus = ConnectionStatus.REQUESTED;
            } else {
                connectionRepository.createNewFollowing(id, followerId, false);
                connectionStatus = ConnectionStatus.FOLLOWING;
            }
        }

        return connectionStatus;
    }

    @Override
    public List<String> getFollowing(String id) {
        return connectionRepository.findFollowingById(id);
    }

    @Override
    public List<String> getFollowingRequests(String id) {
        return connectionRepository.findFollowingRequestsById(id);
    }

    @Override
    public void acceptFollowRequest(String principalId, String id) {
        if(connectionRepository.hasFollowRequest(principalId, id))
            connectionRepository.approveFollowRequest(id, principalId);
        else
            throw new RuntimeException("No follow request with specified id found.");
    }

    @Override
    public void deleteFollowRequest(String principalId, String id) {
        if(connectionRepository.hasFollowRequest(principalId, id))
            connectionRepository.deleteFollowRequest(id, principalId);
        else
            throw new RuntimeException("No follow request with specified id found.");
    }

    @Override
    public List<String> getFollowers(String id) {
        return connectionRepository.findFollowersById(id);
    }

    @Override
    public void deleteFollowing(String principalId, String id) {
        if(connectionRepository.hasRelationship(principalId, id))
            connectionRepository.deleteFollowingOrFollowingRequest(principalId, id);
        else
            throw new RuntimeException("You are not following that profile.");
    }

    @Override
    public void deleteFollower(String principalId, String id) {
        if(connectionRepository.hasRelationship(id, principalId))
            connectionRepository.deleteFollowingOrFollowingRequest(id, principalId);
        else
            throw new RuntimeException("You do not have that follower.");
    }
}
