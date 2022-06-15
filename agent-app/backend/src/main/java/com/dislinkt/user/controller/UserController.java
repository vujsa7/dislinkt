package com.dislinkt.user.controller;

import com.dislinkt.user.dto.UpdatePasswordRequest;
import com.dislinkt.user.model.User;
import com.dislinkt.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Objects;


@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

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

    @PutMapping(value = "/{username}/password")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'COMPANY_OWNER')")
    public ResponseEntity<?> updatePassword(@PathVariable String username, Principal principal, @RequestBody UpdatePasswordRequest request, HttpServletRequest httpRequest){
        if(!Objects.equals(username, principal.getName())){
            log.warn("[" + httpRequest.getRemoteAddr() + "] " + "403 Forbidden for HTTP PUT \"/users/password for user " + principal.getName() + "\"");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.findByEmail(username);
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            log.warn("[" + httpRequest.getRemoteAddr() + "] " + "400 Bad request for HTTP PUT \"/users/password for user " + principal.getName() + "\"");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);

        log.info("[" + httpRequest.getRemoteAddr() + "] " + "200 Ok for HTTP PUT \"/users/password for user " + principal.getName() + "\"");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
