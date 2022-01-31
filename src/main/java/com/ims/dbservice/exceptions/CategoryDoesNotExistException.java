package com.ims.dbservice.exceptions;

public class CategoryDoesNotExistException extends RuntimeException {

    public CategoryDoesNotExistException(String categoryName) {
        super(String.format("Category %s does not exist.", categoryName));
    }
}
