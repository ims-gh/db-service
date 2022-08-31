package com.ims.dbservice.exceptions.advice;

import com.ims.dbservice.exceptions.OrderItemAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderItemAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(OrderItemAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String orderItemAlreadyExistsException(OrderItemAlreadyExistsException ex){
        return ex.getMessage();
    }
}
