package com.dislinkt.user.service;

import com.dislinkt.email.model.Email;
import com.dislinkt.email.service.EmailService;
import com.dislinkt.exception.UserNotFoundException;
import com.dislinkt.exception.UsernameAlreadyExistsException;
import com.dislinkt.user.repository.UserRepository;
import com.dislinkt.user.model.User;
import com.dislinkt.verification.token.model.VerificationToken;
import com.dislinkt.verification.token.service.VerificationTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Value("${env.activation.link}")
    private String baseActivationUrl;

    @Value("${env.reset.password.link}")
    private String baseResetUrl;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        } else {
            return user.get();
        }
    }

    public User loadUser(String s) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(s);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        } else {
            return user.get();
        }
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void createUser(User user) {
        userRepository.findByEmail(user.getEmail()).ifPresent(u -> {
            throw new UsernameAlreadyExistsException();
        });
        userRepository.save(user);
        String token = verificationTokenService.generateToken(user, true);
        emailService.sendEmail(new Email(user.getEmail(), "Account confirmation", "To activate your account click on this link: " + baseActivationUrl + token));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void activateUser(String token) {
        VerificationToken verificationToken = verificationTokenService.validateToken(token);
        User user = verificationToken.getUser();
        user.setIsEnabled(true);
        verificationTokenService.invalidateToken(verificationToken);

        userRepository.save(user);
    }

    @Transactional(rollbackFor = Throwable.class)
    public void sendLinkForPasswordReset(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        String token = verificationTokenService.generateToken(user, false);
        emailService.sendEmail(new Email(user.getEmail(), "Password reset", "To reset your password click on this link: " + baseResetUrl + token));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void resetPassword(String token, String password) {
        VerificationToken verificationToken = verificationTokenService.validateToken(token);
        User user = verificationToken.getUser();
        user.setPassword(password);
        verificationTokenService.invalidateToken(verificationToken);

        userRepository.save(user);
    }
}
