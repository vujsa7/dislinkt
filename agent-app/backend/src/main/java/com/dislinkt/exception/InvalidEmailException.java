package com.dislinkt.exception;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException() {
        super("Invalid email");
    }
}
