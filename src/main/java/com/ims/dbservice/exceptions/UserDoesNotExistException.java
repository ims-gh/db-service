package com.ims.dbservice.exceptions;

public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(String message) {
        super(message);
    }
}
