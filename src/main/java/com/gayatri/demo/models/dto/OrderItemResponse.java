package com.gayatri.demo.models.dto;

import java.math.BigDecimal;
public record OrderItemResponse(
        int quantity,
        String productName,
        int totalPrice,
        int stockQty
) {}
