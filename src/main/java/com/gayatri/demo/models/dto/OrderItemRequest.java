package com.gayatri.demo.models.dto;

public record OrderItemRequest(
        int productId,
        int quantity
) {
}
