package com.ims.dbservice.exceptions;


public class OrderItemAlreadyExistsException extends RuntimeException{

    public OrderItemAlreadyExistsException(String uuid){
        super(String.format("OrderItem with uuid %s already exists.", uuid));
    }
}
