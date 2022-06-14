package com.dislinkt.connectionservice.controller;

import com.dislinkt.connectionservice.dto.ConnectionsDto;
import com.dislinkt.connectionservice.dto.NewConnectionDto;
import com.dislinkt.connectionservice.model.ProfileEntity;
import com.dislinkt.connectionservice.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/connections")
@Slf4j
public class ConnectionController {
    private final ConnectionService connectionService;
    @Autowired
    public ConnectionController(ConnectionService connectionService) {
        this.connectionService = connectionService;
    }

    @PostMapping
    ResponseEntity<NewConnectionDto> postNewConnection(Principal principal, @RequestBody NewConnectionDto newConnectionDto, ServerHttpRequest request){
        if(principal.getName().equals(newConnectionDto.getId())){
            connectionService.createNewConnection(newConnectionDto.getId(), newConnectionDto.getFollowerId());
        } else {
            log.warn("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "401 Unauthorized for HTTP POST \"/connections\"");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<NewConnectionDto>(newConnectionDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity getConnections(Principal principal, @PathVariable String id, ServerHttpRequest request){
        if(principal.getName().equals(id)){
            return new ResponseEntity<>(new ConnectionsDto(connectionService.getAllConnectionsForUser(id)), HttpStatus.OK);
        } else {
            log.warn("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "401 Unauthorized for HTTP GET \"/connections/" + id + "\"");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
