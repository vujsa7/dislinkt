package com.dislinkt.exception;

public class RequestNotFoundException extends RuntimeException {

    public RequestNotFoundException() {
        super("Request with the given id doesn't exist");
    }
}
