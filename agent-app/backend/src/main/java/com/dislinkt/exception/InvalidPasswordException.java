package com.dislinkt.exception;

public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
        super("Password must be more than 8 characters long, and consist of at least 1 uppercase letter and at least 1 special character");
    }
}
