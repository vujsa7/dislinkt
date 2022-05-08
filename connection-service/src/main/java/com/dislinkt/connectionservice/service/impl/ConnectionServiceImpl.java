package com.dislinkt.connectionservice.service.impl;

import com.dislinkt.connectionservice.dao.ConnectionRepository;
import com.dislinkt.connectionservice.model.ProfileEntity;
import com.dislinkt.connectionservice.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Autowired
    public ConnectionServiceImpl(ConnectionRepository connectionRepository) {
        this.connectionRepository = connectionRepository;
    }

    @Override
    public void createNewConnection(String id, String followerId) {
        connectionRepository.createNewConnection(id, followerId);
    }

    @Override
    public List<String> getAllConnectionsForUser(String id) {
        return connectionRepository.findConnectionsById(id);
    }
}
