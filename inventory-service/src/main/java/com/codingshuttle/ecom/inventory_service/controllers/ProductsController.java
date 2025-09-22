package com.codingshuttle.ecom.inventory_service.controllers;
import com.codingshuttle.ecom.inventory_service.clients.OrdersFeignClient;
import com.codingshuttle.ecom.inventory_service.dtos.OrderRequestDto;
import com.codingshuttle.ecom.inventory_service.dtos.OrderRequestItemDto;
import com.codingshuttle.ecom.inventory_service.dtos.ProductDto;
import com.codingshuttle.ecom.inventory_service.services.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductsController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final OrdersFeignClient ordersFeignClient;
    private final OrderRequestDto orderRequestDto;

    @GetMapping("/fetchOrder")
    public String fetchFromOrderService(HttpServletRequest httpServletRequest) {
        log.info(httpServletRequest.getHeader("X-Custom-Header"));

        return ordersFeignClient.helloOrders();  //instead of restClient we use orderFeignClient
    }




    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        log.info("Fetching all products via controller");
        List<ProductDto> inventories = productService.getAllProducts();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        log.info("Fetching product by id via controller");
        ProductDto inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestItemDto orderRequestItemDto)
    {
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }


}