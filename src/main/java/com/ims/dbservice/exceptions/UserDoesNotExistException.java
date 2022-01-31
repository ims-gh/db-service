package com.ims.dbservice.exceptions;

public class UserDoesNotExistException extends RuntimeException {

    // TODO: Refactor to take in user email as parameter and pass default message
    public UserDoesNotExistException(String email) {
        super(String.format("Account with email %s does not exist.", email));

    }
}
