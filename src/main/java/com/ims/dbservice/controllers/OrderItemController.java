package com.ims.dbservice.controllers;

import com.ims.dbservice.exceptions.ResponseHandler;
import com.ims.dbservice.models.dto.OrderItemDTO;
import com.ims.dbservice.models.entities.OrderItem;
import com.ims.dbservice.services.impl.OrderItemServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1")
@AllArgsConstructor
public class OrderItemController {

    private final OrderItemServiceImpl orderItemServiceImpl;

    @GetMapping("/order-items")
    public ResponseEntity<Object> getAllOrderItems(){
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(orderItemServiceImpl.getAllOrderItems())
                .build();
    }

    @GetMapping("/order-item")
    public ResponseEntity<Object> getOrderItemById(@RequestParam String uuid){
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(orderItemServiceImpl.getByOrderItemId(uuid))
                .build();
    }

    @PostMapping("/order-item")
    public ResponseEntity<Object> addNewOrderItem(@RequestBody OrderItem orderItem){
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .data(orderItemServiceImpl.addNewOrderItem(orderItem))
                .message("OrderItem successfully created")
                .build();
    }

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
