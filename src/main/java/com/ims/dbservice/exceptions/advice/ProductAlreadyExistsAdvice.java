package com.ims.dbservice.exceptions.advice;

import com.ims.dbservice.exceptions.ProductAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(ProductAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String productAlreadyExistsHandler(ProductAlreadyExistsException ex){
        return ex.getMessage();
    }
}
