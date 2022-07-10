package com.dislinkt.auth.service.impl;

import com.dislinkt.auth.repository.AuthLoginCodeRepository;
import com.dislinkt.auth.model.AuthLoginCode;
import com.dislinkt.auth.service.AuthLoginCodeService;
import com.dislinkt.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthLoginCodeServiceImpl implements AuthLoginCodeService {

    private final UserRepository userRepository;
    private final AuthLoginCodeRepository authLoginCodeRepository;

    public AuthLoginCodeServiceImpl(UserRepository userRepository, AuthLoginCodeRepository authLoginCodeRepository) {
        this.userRepository = userRepository;
        this.authLoginCodeRepository = authLoginCodeRepository;
    }

    @Transactional
    @Override
    public String createAuthLoginCode(String email) {
        String otpCode = generateOtp();
        AuthLoginCode authLoginCode = new AuthLoginCode();
        authLoginCode.setOtp(otpCode);
        authLoginCode.setUser(userRepository.findByEmail(email).get());
        Calendar date = Calendar.getInstance();
        long timeInSecs = date.getTimeInMillis();
        Date afterAdding3Mins = new Date(timeInSecs + (3 * 60 * 1000));
        authLoginCode.setExpirationDate(afterAdding3Mins);
        authLoginCodeRepository.deleteAllByUserId(authLoginCode.getUser().getId());
        authLoginCodeRepository.save(authLoginCode);
        return authLoginCode.getOtp();
    }

    @Override
    public boolean verifyOtp(String otp, String email) {
        Optional<AuthLoginCode> authLoginCodeOptional = authLoginCodeRepository.findByOtp(otp);
        if(authLoginCodeOptional.isEmpty())
            return false;
        else {
            if(!authLoginCodeOptional.get().getUser().getEmail().equals(email) || authLoginCodeOptional.get().getExpirationDate().compareTo(new Date()) < 0)
                return false;
            authLoginCodeRepository.delete(authLoginCodeOptional.get());
            return true;
        }
    }

    private String generateOtp() {
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[6];
        for (int i = 0; i < 6; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return String.valueOf(otp);
    }


}
