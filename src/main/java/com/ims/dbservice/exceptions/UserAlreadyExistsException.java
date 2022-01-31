package com.ims.dbservice.exceptions;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String email) {
        super(String.format("Account with email %s already exists.", email));
    }
}
