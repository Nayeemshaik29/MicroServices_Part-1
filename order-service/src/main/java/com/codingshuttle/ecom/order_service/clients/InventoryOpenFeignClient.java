package com.codingshuttle.ecom.order_service.clients;


import com.codingshuttle.ecom.order_service.dtos.OrderRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="inventory-service",path = "/inventory")  //name = application name, path = base path
public interface InventoryOpenFeignClient {

    @PutMapping("/products/reduce-stocks")  //use same path and mapping
    Double reduceStocks(@RequestBody OrderRequestDto orderRequestDto);  //use same method as order-service

// The OrderRequestDto class is already created inside the previous article.
}
