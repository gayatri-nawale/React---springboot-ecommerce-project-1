package com.gayatri.demo.services;

import com.gayatri.demo.models.Order;
import com.gayatri.demo.models.OrderItem;
import com.gayatri.demo.models.Product;
import com.gayatri.demo.models.dto.OrderItemRequest;
import com.gayatri.demo.models.dto.OrderItemResponse;
import com.gayatri.demo.models.dto.OrderRequest;
import com.gayatri.demo.models.dto.OrderResponse;
import com.gayatri.demo.repository.Orderrepo;
import com.gayatri.demo.repository.Productrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private Productrepo productrepo;
    @Autowired
    private Orderrepo orderrepo;


    public OrderResponse placeorder(OrderRequest orderRequest) {
        Order order=new Order();
        String orderid="ORD"+ UUID.randomUUID().toString().substring(0,8).toUpperCase();
        order.setOrderid(orderid);
        order.setCustomerName(orderRequest.customerName());
        order.setEmail(orderRequest.email());
        order.setStatus("PLACED");
        order.setOrderDate(LocalDate.now());

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest req : orderRequest.items()) {

            Product p = productrepo.findById(req.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            p.setStockQty(p.getStockQty() - req.quantity());
            productrepo.save(p);

            OrderItem item = new OrderItem();
            item.setProduct(p);
            item.setQuantity(req.quantity());
            item.setTotalPrice(p.getPrice() * req.quantity());
            item.setOrder(order);

            orderItems.add(item);
        }
        order.setItems(orderItems);
        Order savedorder=orderrepo.save(order);
        List<OrderItemResponse> itemResponses=new ArrayList<>();
        for(OrderItem item:order.getItems()){
            OrderItemResponse orderItemResponse=new OrderItemResponse(
                    item.getQuantity(),
                    item.getProduct().getName(),
                    item.getTotalPrice(),
                    item.getProduct().getStockQty()
            );
itemResponses.add(orderItemResponse);
        }
        OrderResponse orderresponse=new OrderResponse(savedorder.getOrderid(),savedorder.getCustomerName(),savedorder.getEmail(),savedorder.getStatus(),savedorder.getOrderDate(),itemResponses);
return orderresponse;
    }

    public List<OrderResponse> getorders() {

        List<Order> orders = orderrepo.findAll();
        List<OrderResponse> responses = new ArrayList<>();

        for (Order order : orders) {

            List<OrderItemResponse> itemResponses = new ArrayList<>();

            for (OrderItem item : order.getItems()) {
                itemResponses.add(new OrderItemResponse(
                        item.getQuantity(),
                        item.getProduct().getName(),
                        item.getTotalPrice(),
                        item.getProduct().getStockQty()
                ));
            }

            responses.add(new OrderResponse(
                    order.getOrderid(),
                    order.getCustomerName(),
                    order.getEmail(),
                    order.getStatus(),
                    order.getOrderDate(),
                    itemResponses
            ));
        }

        return responses;
    }
}
