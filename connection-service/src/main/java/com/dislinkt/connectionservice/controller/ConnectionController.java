package com.dislinkt.connectionservice.controller;

import com.dislinkt.connectionservice.dto.FollowingsDto;
import com.dislinkt.connectionservice.dto.NewConnectionDto;
import com.dislinkt.connectionservice.dto.ConnectionStatusDto;
import com.dislinkt.connectionservice.model.ConnectionStatus;
import com.dislinkt.connectionservice.service.ConnectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        return new ResponseEntity(new ConnectionStatusDto(connectionStatus.toString()), HttpStatus.CREATED);
    }

    @GetMapping(value = "/followings")
    ResponseEntity getAllFollowings(Principal principal){
        List<String> following = connectionService.getFollowing(principal.getName());
        List<String> requests = connectionService.getFollowingRequests(principal.getName());
        return new ResponseEntity<>(new FollowingsDto(following, requests), HttpStatus.OK);
    }

    @PostMapping(value = "following-requests/accept/{id}")
    ResponseEntity acceptFollowRequest(Principal principal, @PathVariable String id){
        try{
            connectionService.acceptFollowRequest(principal.getName(), id);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "following-requests/delete/{id}")
    ResponseEntity deleteFollowRequest(Principal principal, @PathVariable String id){
        try{
            connectionService.deleteFollowRequest(principal.getName(), id);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "following/delete/{id}")
    ResponseEntity unfollowProfile(Principal principal, @PathVariable String id){
        try{
            connectionService.deleteFollowing(principal.getName(), id);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "followers/delete/{id}")
    ResponseEntity deleteFollower(Principal principal, @PathVariable String id){
        try{
            connectionService.deleteFollower(principal.getName(), id);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }
}
