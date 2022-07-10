package com.dislinkt.exception;

public class CompanyNotFoundException extends RuntimeException {

    public CompanyNotFoundException() {
        super("Company with the provided id doesn't exist");
    }
}
