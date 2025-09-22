package com.codingshuttle.ecom.inventory_service.dtos;

import lombok.Data;

@Data
public class ProductDto {
    private Long id;
    private String title;

    private Double price;

    private Integer stock;
}