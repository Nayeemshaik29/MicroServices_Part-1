package com.codingshuttle.ecom.order_service.dtos;

import lombok.Data;

@Data
public class OrderRequestItemDto {
    private Long id;
    private Long productId;
    private Integer quantity;
}
