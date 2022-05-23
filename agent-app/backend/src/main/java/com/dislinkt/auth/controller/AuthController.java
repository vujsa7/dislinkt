package com.dislinkt.auth.controller;

import com.dislinkt.auth.dto.SignInRequest;
import com.dislinkt.auth.dto.SignInResponse;
import com.dislinkt.user.model.User;
import com.dislinkt.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.concurrent.atomic.LongAccumulator;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<SignInResponse> createAuthenticationToken(@Valid @RequestBody SignInRequest request){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());

        return ResponseEntity.ok(new SignInResponse(jwt));
    }

}