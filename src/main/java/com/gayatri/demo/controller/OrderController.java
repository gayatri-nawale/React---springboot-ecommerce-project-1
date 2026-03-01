package com.gayatri.demo.controller;

import com.gayatri.demo.models.Order;
import com.gayatri.demo.models.OrderItem;
import com.gayatri.demo.models.dto.OrderRequest;
import com.gayatri.demo.models.dto.OrderResponse;
import com.gayatri.demo.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/orders")
@RestController
public class OrderController {
    @Autowired
    private OrderService service;

    @PostMapping("/place")
    public ResponseEntity<OrderResponse> placeorder(@RequestBody OrderRequest orderRequest){
        OrderResponse or=service.placeorder(orderRequest);
        return new ResponseEntity<>(or, HttpStatus.CREATED);
    }


    // ✅ ADD THIS METHOD
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return service.getorders();
    }
}
