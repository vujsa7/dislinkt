package com.dislinkt.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> preauthorizeUser() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> preauthorizeAdmin() {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/company-owner")
    @PreAuthorize("hasRole('COMPANY_OWNER')")
    public ResponseEntity<?> preauthorizeCompanyOwner() {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
