package com.dislinkt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { VerificationTokenInvalidException.class, InvalidPasswordException.class, InvalidEmailException.class })
    public ResponseEntity<Object> handleVerificationTokenException(VerificationTokenInvalidException ex) {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public ResponseEntity<Object> handleUsernameExistsException(UsernameAlreadyExistsException ex) {
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }
}
