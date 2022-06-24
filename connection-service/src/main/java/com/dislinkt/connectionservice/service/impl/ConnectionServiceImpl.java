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
            connectionRepository.deleteConnection(id, followerId);
            connectionStatus = ConnectionStatus.NO_FOLLOW;
        }
        else{
            if(isFollowerPrivate){
                connectionRepository.createNewConnectionRequest(id, followerId, true);
                connectionStatus = ConnectionStatus.REQUESTED;
            } else {
                connectionRepository.createNewConnection(id, followerId, false);
                connectionStatus = ConnectionStatus.FOLLOWING;
            }
        }

        return connectionStatus;
    }

    @Override
    public List<String> getAllConnectionsForUser(String id) {
        return connectionRepository.findConnectionsById(id);
    }

    @Override
    public List<String> getAllFollowRequestsForUser(String id) {
        return connectionRepository.findFollowRequestsById(id);
    }
}
