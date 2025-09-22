package com.codingshuttle.ecom.inventory_service.dtos;


import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDto {


    private List<OrderRequestItemDto> items;


}
