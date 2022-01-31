package com.ims.dbservice.exceptions.advice;

import com.ims.dbservice.exceptions.CategoryAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CategoryAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(CategoryAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String categoryAlreadyExistsHandler(CategoryAlreadyExistsException ex){
        return ex.getMessage();
    }
}
