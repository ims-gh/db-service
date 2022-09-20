package com.ims.ordermanagement.exceptions;

public class OrderDoesNotExistException extends RuntimeException{

    public OrderDoesNotExistException(String id){
        super(String.format("Order with id %s does not exist.", id));
    }
}
