package com.dislinkt.verification.token.service;

import com.dislinkt.exception.VerificationTokenInvalidException;
import com.dislinkt.user.model.User;
import com.dislinkt.verification.token.dao.VerificationTokenRepository;
import com.dislinkt.verification.token.model.VerificationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VerificationTokenService {

    private final VerificationTokenRepository repository;
    private static final int EXPIRES_IN_ONE_HOUR = 60;
    private static final int EXPIRES_IN_ONE_YEAR = 525960;

    @Transactional(rollbackFor = Throwable.class)
    public String generateToken(User user, boolean validForOneYear) {
        VerificationToken token = VerificationToken.builder()
                .token(UUID.randomUUID())
                .user(user)
                .expirationDate(validForOneYear ? getExpirationDate(EXPIRES_IN_ONE_YEAR) : getExpirationDate(EXPIRES_IN_ONE_HOUR))
                .used(false)
                .build();
        repository.save(token);

        return token.getToken().toString();
    }

    @Transactional(readOnly = true, rollbackFor = Throwable.class)
    public VerificationToken validateToken(String token) {
        Optional<VerificationToken> verificationToken = repository.findByToken(UUID.fromString(token));
        if (verificationToken.isEmpty() || verificationToken.get().getExpirationDate().getTime() - new Date().getTime() <= 0 || verificationToken.get().isUsed()) {
            throw new VerificationTokenInvalidException();
        }

        return verificationToken.get();
    }

    @Transactional(rollbackFor = Throwable.class)
    public void invalidateToken(VerificationToken verificationToken) {
        verificationToken.setUsed(true);
        repository.save(verificationToken);
    }

    private Date getExpirationDate(int validFor) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, validFor);
        return calendar.getTime();
    }
}
