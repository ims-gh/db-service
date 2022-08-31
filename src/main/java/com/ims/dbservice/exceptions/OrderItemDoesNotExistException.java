package com.ims.dbservice.exceptions;

public class OrderItemDoesNotExistException extends RuntimeException{

    public OrderItemDoesNotExistException(String uuid){
        super(String.format("OrderItem with uuid %s does not exist.", uuid));
    }
}
