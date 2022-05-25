package com.dislinkt.auth.controller;

import com.dislinkt.auth.dto.*;
import com.dislinkt.user.model.User;
import com.dislinkt.user.service.RoleService;
import com.dislinkt.user.service.UserService;
import com.dislinkt.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<SignInResponse> createAuthenticationToken(@Valid @RequestBody SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());

        return ResponseEntity.ok(new SignInResponse(jwt));
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        userService.createUser(User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(roleService.getRole("ROLE_USER"))
                .isEnabled(false)
                .isDeleted(false)
                .build());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/forgot-password")
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        userService.sendLinkForPasswordReset(request.getEmail());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        userService.resetPassword(request.getToken(), passwordEncoder.encode(request.getPassword()));

        return new ResponseEntity<>(HttpStatus.OK);
    }
}