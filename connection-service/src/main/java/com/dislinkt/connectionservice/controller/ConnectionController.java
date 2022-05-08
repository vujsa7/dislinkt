package com.dislinkt.connectionservice.controller;

import com.dislinkt.connectionservice.dto.NewConnectionDto;
import com.dislinkt.connectionservice.service.ConnectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
}
