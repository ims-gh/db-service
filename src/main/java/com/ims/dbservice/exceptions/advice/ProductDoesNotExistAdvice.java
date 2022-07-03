package com.ims.dbservice.exceptions.advice;

import com.ims.dbservice.exceptions.ProductDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ProductDoesNotExistAdvice {

    @ResponseBody
    @ExceptionHandler(ProductDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String productDoesNotExistHandler(ProductDoesNotExistException ex){
        return ex.getMessage();
    }
}
