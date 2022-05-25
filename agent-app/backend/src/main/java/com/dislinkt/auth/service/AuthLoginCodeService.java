package com.dislinkt.auth.service;

public interface AuthLoginCodeService {
    String createAuthLoginCode(String email);
    boolean verifyOtp(String otp, String username);
}
