package com.codingshuttle.ecom.inventory_service.dtos;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}
