package com.ims.dbservice.exceptions.advice;

import com.ims.dbservice.exceptions.OrderItemDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderItemDoesNotExistAdvice {

    @ResponseBody
    @ExceptionHandler(OrderItemDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String orderItemDoesNotExistHandler(OrderItemDoesNotExistException ex){
        return ex.getMessage();
    }
}
