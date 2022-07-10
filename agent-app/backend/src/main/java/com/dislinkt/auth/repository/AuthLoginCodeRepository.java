package com.dislinkt.auth.repository;

import com.dislinkt.auth.model.AuthLoginCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AuthLoginCodeRepository extends JpaRepository<AuthLoginCode, UUID> {
    Optional<AuthLoginCode> findByOtp(String otp);
    void deleteAllByUserId(UUID id);
}
