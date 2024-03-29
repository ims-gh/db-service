package com.ims.ordermanagement.controllers;

import com.ims.ordermanagement.exceptions.ResponseHandler;
import com.ims.ordermanagement.models.OrderBody;
import com.ims.ordermanagement.models.dto.OrderBodyDTO;
import com.ims.ordermanagement.models.entities.Order;
import com.ims.ordermanagement.services.impl.OrderServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "v1")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderService;

    @ApiOperation(value = "Get a list of all orders")
    @GetMapping("/orders")
    public ResponseEntity<Object> getAllOrders() {
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(orderService.getAllOrders())
                .build();
    }

    @ApiOperation(value = "Get order by parameters")
    @GetMapping("/order")
    public ResponseEntity<Object> getOrderBy(@RequestParam Map<String, String> params) {
        return ResponseHandler
                .builder()
                .status(HttpStatus.FOUND)
                .data(getOrdersByParams(params))
                .build();
    }

    @ApiOperation(value = "Add a new order")
    @PostMapping("/order")
    public ResponseEntity<Object> addNewOrder(@RequestBody OrderBody orderBody) {
        return ResponseHandler
                .builder()
                .status(HttpStatus.CREATED)
                .data(orderService.addNewOrder(orderBody))
                .message("Order successfully created")
                .build();
    }

    @ApiOperation(value = "Update an existing order")
    @PatchMapping("/order/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable("id") String id,
                                              @RequestBody OrderBodyDTO orderBodyDTO) {
        orderService.updateOrder(id, orderBodyDTO);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .data(orderBodyDTO)
                .message("Order successfully updated.")
                .build();
    }

    @ApiOperation(value = "Delete an existing order")
    @DeleteMapping("/order")
    public ResponseEntity<Object> deleteOrder(@RequestParam String id) {
        orderService.deleteOrder(id);
        return ResponseHandler
                .builder()
                .status(HttpStatus.OK)
                .message("Order successfully deleted.")
                .build();
    }

    private List<Order> getOrdersByParams(Map<String, String> params) {
        List<Order> data = new ArrayList<>();
        switch (params.size()){
            case 1:
                if (params.containsKey("id")) {
                    data = orderService.getByOrderIds(Arrays.asList((params.get("id")).split(",")));
                } else if (params.containsKey("location")) {
                    data = orderService.getByDeliveryLocations(Arrays.asList((params.get("location")).split(",")));
                } else if (params.containsKey("status")) {
                    data = orderService.getByOrderStatuses(Arrays.asList((params.get("status")).split(",")));
                } else if (params.containsKey("buyersName")) {
                    data = orderService.getByBuyersName(params.get("buyersName"));
                } else if (params.containsKey("recipientsName")) {
                    data = orderService.getByRecipientsName(params.get("recipientsName"));
                } else if (params.containsKey("paymentMethod")) {
                    data = orderService.getByPaymentMethod(params.get("paymentMethod"));
                }
                break;
            case 2:
                if (params.containsKey("deliveryStartDate") && params.containsKey("deliveryEndDate")) {
                    data = orderService.getByDeliveryDate(params.get("deliveryStartDate"), params.get("deliveryEndDate"));
                } else if (params.containsKey("orderStartDate") && params.containsKey("orderEndDate")) {
                    data = orderService.getByOrderDate(params.get("orderStartDate"), params.get("orderEndDate"));
                } else if (params.containsKey("status") && params.containsKey("location")) {
                    data = orderService.getByOrderStatusesAndDeliveryLocations(Arrays.asList((params.get("status")).split(",")), Arrays.asList((params.get("location")).split(",")));
                }
                break;
            case 3:
                if (params.containsKey("deliveryStartDate") && params.containsKey("deliveryEndDate") && params.containsKey("status")) {
                    data = orderService.getByDeliveryDateAndOrderStatuses(params.get("deliveryStartDate"), params.get("deliveryEndDate"), Arrays.asList((params.get("status")).split(",")));
                }
                break;
        }
        return data;
    }
}
