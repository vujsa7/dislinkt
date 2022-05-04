package com.dislinkt.profileservice.exception;

public class UsernameAlreadyExistsException extends RuntimeException{

    public UsernameAlreadyExistsException() {
        super("Profile with given username already exists");
    }
}
