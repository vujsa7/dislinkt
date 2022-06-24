package com.dislinkt.connectionservice.controller;

import com.dislinkt.connectionservice.dto.ConnectionsRequestsDto;
import com.dislinkt.connectionservice.dto.NewConnectionDto;
import com.dislinkt.connectionservice.dto.NewConnectionStatusDto;
import com.dislinkt.connectionservice.model.ConnectionStatus;
import com.dislinkt.connectionservice.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(value = "/connections")
@Slf4j
public class ConnectionController {
    private final ConnectionService connectionService;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public ConnectionController(ConnectionService connectionService, WebClient.Builder webClientBuilder) {
        this.connectionService = connectionService;
        this.webClientBuilder = webClientBuilder;
    }

    @PostMapping
    ResponseEntity modifyConnection(Principal principal, @RequestBody NewConnectionDto newConnectionDto, JwtAuthenticationToken jwt){
        ConnectionStatus connectionStatus = connectionService.modifyConnection(principal.getName(), newConnectionDto.getFollowerId(), newConnectionDto.getIsFollowerPrivate());
        return new ResponseEntity(new NewConnectionStatusDto(connectionStatus.toString()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    ResponseEntity getConnections(Principal principal, @PathVariable String id, ServerHttpRequest request){
        if(principal.getName().equals(id)){
            List<String> connections = connectionService.getAllConnectionsForUser(id);
            List<String> requests = connectionService.getAllFollowRequestsForUser(id);
            return new ResponseEntity<>(new ConnectionsRequestsDto(connections, requests), HttpStatus.OK);
        } else {
            log.warn("[" + request.getRemoteAddress().getAddress().getHostAddress() + "] " + "401 Unauthorized for HTTP GET \"/connections/" + id + "\"");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
