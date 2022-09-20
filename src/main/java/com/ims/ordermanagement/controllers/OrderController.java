package com.ims.ordermanagement.controllers;

import com.ims.ordermanagement.exceptions.ResponseHandler;
import com.ims.ordermanagement.models.dto.OrderDTO;
import com.ims.ordermanagement.models.entities.Order;
import com.ims.ordermanagement.services.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "v1")
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrders() {
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(orderService.getAllOrders())
                .build();
    }

    @GetMapping("/order")
    public ResponseEntity<Object> getOrderBy(@RequestParam(name = "id", required = false) List<String> ids,
                                             @RequestParam(name = "location", required = false) List<String> locations,
                                             @RequestParam(name = "deliveryStartDate", required = false) String deliveryStartDate,
                                             @RequestParam(name = "deliveryEndDate", required = false) String deliveryEndDate,
                                             @RequestParam(name = "orderStartDate", required = false) String orderStartDate,
                                             @RequestParam(name = "orderEndDate", required = false) String orderEndDate,
                                             @RequestParam(name = "status", required = false) List<String> statuses
    ) {
        Object data = null;
        if (ids != null) {
            data = orderService.getByOrderIds(ids);
        } else if (locations != null) {
            data = orderService.getByDeliveryLocations(locations);
        } else if (deliveryStartDate != null && deliveryEndDate != null) {
            data = orderService.getByDeliveryDate(deliveryStartDate, deliveryEndDate);
        } else if (orderStartDate != null && orderEndDate != null) {
            data = orderService.getByOrderDate(orderStartDate, orderEndDate);
        } else if (statuses != null){
            data = orderService.getByOrderStatuses(statuses);
        }
        ResponseHandler response = ResponseHandler.builder().data(data);
        return ((data != null && !data.toString().isBlank()) ? response.status(HttpStatus.FOUND) : response.status(HttpStatus.OK)).build();
    }


    @PostMapping("/order")
    public ResponseEntity<Object> addNewOrder(@RequestBody Order order) {
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .data(orderService.addNewOrder(order))
                .message("Order successfully created")
                .build();
    }

    @PatchMapping("/order/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable("id") String id,
                                              @RequestBody OrderDTO orderDTO) {
        orderService.updateOrder(id, orderDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(orderDTO)
                .message("Order successfully updated.")
                .build();
    }


    @DeleteMapping("/order")
    public ResponseEntity<Object> deleteOrder(@RequestParam String id){
        orderService.deleteOrder(id);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("Order successfully deleted.")
                .build();
    }
}
