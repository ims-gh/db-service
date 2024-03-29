package com.ims.ordermanagement.exceptions;

public class ProductAlreadyExistsException extends RuntimeException {

    public ProductAlreadyExistsException(String productSlug) {
        super(String.format("Product %s already exists.", productSlug));
    }
}
