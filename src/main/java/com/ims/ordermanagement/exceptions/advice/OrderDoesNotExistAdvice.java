package com.ims.ordermanagement.exceptions.advice;

import com.ims.ordermanagement.exceptions.OrderDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderDoesNotExistAdvice {

    @ResponseBody
    @ExceptionHandler(OrderDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderDoesNotExistHandler(OrderDoesNotExistException ex){
        return ex.getMessage();
    }
}
