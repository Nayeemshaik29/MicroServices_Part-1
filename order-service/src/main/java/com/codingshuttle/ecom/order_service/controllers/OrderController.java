package com.codingshuttle.ecom.order_service.controllers;

import com.codingshuttle.ecom.order_service.clients.InventoryOpenFeignClient;
import com.codingshuttle.ecom.order_service.dtos.OrderRequestDto;
import com.codingshuttle.ecom.order_service.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/core")
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    @GetMapping("/helloOrders")
    public String helloOrders(){
        return "Hello from OrderService";
    }

    @PostMapping("/create-order")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto)
    {
        OrderRequestDto orderRequestDto1 = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);

    }




    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(){
        log.info("Fetching all orders via controller");
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id){
        log.info("Fetching order by id via controller");
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

}