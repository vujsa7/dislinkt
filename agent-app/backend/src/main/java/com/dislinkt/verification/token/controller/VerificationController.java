package com.dislinkt.verification.token.controller;

import com.dislinkt.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VerificationController {

    private final UserService userService;

    @GetMapping(value = "/account-activation")
    public ResponseEntity<String> activateAccount(@RequestParam(value = "token") String token) {
        userService.activateUser(token);

        return new ResponseEntity<>("Account successfully activated", HttpStatus.OK);
    }
}