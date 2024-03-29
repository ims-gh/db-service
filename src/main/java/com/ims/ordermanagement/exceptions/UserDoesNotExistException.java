package com.ims.ordermanagement.exceptions;

public class UserDoesNotExistException extends RuntimeException {

    public UserDoesNotExistException(String email) {
        super(String.format("Account with email %s does not exist.", email));

    }
}
