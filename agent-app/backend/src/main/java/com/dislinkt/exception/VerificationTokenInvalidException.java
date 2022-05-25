package com.dislinkt.exception;

public class VerificationTokenInvalidException extends RuntimeException{

    public VerificationTokenInvalidException() {
        super("Token invalid");
    }
}
