package com.dislinkt.connectionservice.controller;

import com.dislinkt.connectionservice.dto.ConnectionsDto;
import com.dislinkt.connectionservice.dto.NewConnectionDto;
import com.dislinkt.connectionservice.model.ProfileEntity;
import com.dislinkt.connectionservice.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/connections")
public class ConnectionController {
    private final ConnectionService connectionService;
    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping
    ResponseEntity<NewConnectionDto> postNewConnection(Principal principal, @RequestBody NewConnectionDto newConnectionDto){
        if(principal.getName().equals(newConnectionDto.getId())){
            connectionService.createNewConnection(newConnectionDto.getId(), newConnectionDto.getFollowerId());
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<NewConnectionDto>(newConnectionDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity getConnections(Principal principal, @PathVariable String id){
        List<ProfileEntity> profiles = new ArrayList<>();
        if(principal.getName().equals(id)){
            return new ResponseEntity<>(new ConnectionsDto(connectionService.getAllConnectionsForUser(id)), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
