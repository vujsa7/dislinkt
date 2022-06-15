package com.dislinkt.auth.controller;

import com.dislinkt.auth.dto.*;
import com.dislinkt.auth.dto.OtpSignInRequest;
import com.dislinkt.auth.dto.SignInRequest;
import com.dislinkt.auth.dto.SignInResponse;
import com.dislinkt.auth.dto.SignUpRequest;
import com.dislinkt.auth.service.AuthLoginCodeService;
import com.dislinkt.email.model.Email;
import com.dislinkt.email.service.EmailService;
import com.dislinkt.exception.InvalidEmailException;
import com.dislinkt.exception.InvalidPasswordException;
import com.dislinkt.user.model.User;
import com.dislinkt.user.service.RoleService;
import com.dislinkt.user.service.UserService;
import com.dislinkt.util.TokenUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final TokenUtils tokenUtils;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final EmailService emailService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final Pattern passwordPattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[#?!@$%^&*-]).{8,}");
    private final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)" +
            "*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")" +
            "@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            "\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]" +
            "|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
    private final AuthLoginCodeService authLoginCodeService;

    @PostMapping(value = "/sign-in")
    public ResponseEntity<SignInResponse> createAuthenticationToken(@Valid @RequestBody SignInRequest request, HttpServletRequest httpRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);


            User user = (User) authentication.getPrincipal();
            String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());

            log.info("[" + httpRequest.getRemoteAddr() + "] " + httpRequest.getHeader("User-Agent") + " Successful login. User: " + request.getUsername());
            return ResponseEntity.ok(new SignInResponse(jwt));
        } catch (BadCredentialsException e) {
            log.warn("[" + httpRequest.getRemoteAddr() + "] " + httpRequest.getHeader("User-Agent") + " Failed login. Bad credentials for user: " + request.getUsername());
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/sign-in-otp")
    public ResponseEntity createOneTimePassword(@Valid @RequestBody String email) {

        try{
            UserDetails userDetails = userService.loadUserByUsername(email);
            String otp = authLoginCodeService.createAuthLoginCode(userDetails.getUsername());
            emailService.sendEmail(new Email(userDetails.getUsername(), "Dislinkt Login Request", "This OTP will expire in 3 minutes, you can use it to login to your account: " + otp));
            return new ResponseEntity("Check your email and use OTP to login.", HttpStatus.CREATED);
        } catch (UsernameNotFoundException e){
            return new ResponseEntity("Username does not exist!", HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/sign-in-otp-verify")
    public ResponseEntity<?> verifyOneTimePassword(@Valid @RequestBody OtpSignInRequest request, HttpServletRequest httpRequest) {

        if(authLoginCodeService.verifyOtp(request.getOtp(), request.getUsername())){
            User user = userService.loadUser(request.getUsername());
            Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(), null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenUtils.generateToken(user.getUsername(), user.getRole().getName());
            return ResponseEntity.ok(new SignInResponse(jwt));
        }
        log.warn("[" + httpRequest.getRemoteAddr() + "] " + "400 Bad request for HTTP POST \"/sign-in-otp-verify/ for user " + request.getUsername() + "\"");
        return new ResponseEntity<>("Invalid one time password!", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest request) {
        Matcher passwordMatcher = passwordPattern.matcher(request.getPassword());
        if (!passwordMatcher.matches()) {
            throw new InvalidPasswordException();
        }
        Matcher emailMatcher = emailPattern.matcher(request.getEmail());
        if (!emailMatcher.matches()) {
            throw new InvalidEmailException();
        }
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
    public ResponseEntity<?> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request, HttpServletRequest httpRequest) {
        userService.sendLinkForPasswordReset(request.getEmail());

        log.info("[" + httpRequest.getRemoteAddr() + "] " + "200 Ok for HTTP POST \"/auth/forgot-password for user " + request.getEmail() + "\"");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request, HttpServletRequest httpRequest) {
        userService.resetPassword(request.getToken(), passwordEncoder.encode(request.getPassword()));

        log.info("[" + httpRequest.getRemoteAddr() + "] " + "200 Ok for HTTP POST \"/auth/reset-password\"");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}