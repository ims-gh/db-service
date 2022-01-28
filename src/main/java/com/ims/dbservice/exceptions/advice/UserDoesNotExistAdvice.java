package com.ims.dbservice.exceptions.advice;

import com.ims.dbservice.exceptions.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserDoesNotExistAdvice {

    @ResponseBody
    @ExceptionHandler(UserDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String userDoesNotExistHandler(UserDoesNotExistException ex){
        return ex.getMessage();
    }
}
