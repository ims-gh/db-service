package com.ims.dbservice.exceptions;

public class CategoryAlreadyExistsException extends RuntimeException {

    public CategoryAlreadyExistsException(String categoryName) {
        super(String.format("Category %s already exists.", categoryName));
    }
}
