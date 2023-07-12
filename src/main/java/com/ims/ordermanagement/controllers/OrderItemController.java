package com.ims.ordermanagement.controllers;

import com.ims.ordermanagement.exceptions.ResponseHandler;
import com.ims.ordermanagement.models.dto.OrderItemDTO;
import com.ims.ordermanagement.models.entities.OrderItem;
import com.ims.ordermanagement.services.impl.OrderItemServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1")
public class OrderItemController {

    @Autowired
    private OrderItemServiceImpl orderItemServiceImpl;

    @ApiOperation(value = "Get a list of all order items")
    @GetMapping("/order-items")
    public ResponseEntity<Object> getAllOrderItems(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(orderItemServiceImpl.getAllOrderItems())
                .build();
    }

    @ApiOperation(value = "Get order item by UUID")
    @GetMapping("/order-item")
    public ResponseEntity<Object> getOrderItemById(@RequestParam String uuid){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(orderItemServiceImpl.getByOrderItemId(uuid))
                .build();
    }

    @ApiOperation(value = "Add a new order item")
    @PostMapping("/order-item")
    public ResponseEntity<Object> addNewOrderItem(@RequestBody OrderItem orderItem){
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .data(orderItemServiceImpl.addNewOrderItem(orderItem))
                .message("OrderItem successfully created")
                .build();
    }

    @ApiOperation(value = "Update an existing order item")
    @PatchMapping("/order-item/{uuid}")
    public ResponseEntity<Object> updateOrderItem(@PathVariable("uuid") String uuid,
                                                 @RequestBody OrderItemDTO orderItemDTO){
        orderItemServiceImpl.updateOrderItem(uuid, orderItemDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(orderItemDTO)
                .message("OrderItem successfully updated.")
                .build();
    }

    @ApiOperation(value = "Delete an existing order item")
    @DeleteMapping("/order-item")
    public ResponseEntity<Object> deleteOrderItem(@RequestParam String uuid){
        orderItemServiceImpl.deleteOrderItem(uuid);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("OrderItem successfully deleted.")
                .build();
    }
}
