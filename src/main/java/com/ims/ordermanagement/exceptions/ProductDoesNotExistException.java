package com.ims.ordermanagement.exceptions;

public class ProductDoesNotExistException extends RuntimeException {

    public ProductDoesNotExistException(String productSlug) {
        super(String.format("Product %s does not exist.", productSlug));
    }
}
