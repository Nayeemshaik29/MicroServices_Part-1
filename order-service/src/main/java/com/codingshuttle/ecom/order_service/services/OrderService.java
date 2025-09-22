package com.codingshuttle.ecom.order_service.services;

import com.codingshuttle.ecom.order_service.clients.InventoryOpenFeignClient;
import com.codingshuttle.ecom.order_service.dtos.OrderRequestDto;
import com.codingshuttle.ecom.order_service.entities.OrderItemsEntity;
import com.codingshuttle.ecom.order_service.entities.OrderStatus;
import com.codingshuttle.ecom.order_service.entities.OrdersEntity;
import com.codingshuttle.ecom.order_service.repositories.OrderRepo;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepo orderRepo;
    private final ModelMapper modelMapper;
    private final InventoryOpenFeignClient inventoryOpenFeignClient;

    public List<OrderRequestDto> getAllOrders(){
        log.info("Fetching all orders");
        List<OrdersEntity> orders = orderRepo.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderRequestDto.class))
                .toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Fetching order by id");
        OrdersEntity order = orderRepo.findById(id).orElseThrow(()->new RuntimeException("Order not found"));
        return modelMapper.map(order, OrderRequestDto.class);
    }

    @Retry(name = "inventoryRetry", fallbackMethod = "createOrderFallback")
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto) {
        log.info("Calling the createOrder method");
        Double totalPrice = inventoryOpenFeignClient.reduceStocks(orderRequestDto);

        //OrderEntity is already created inside the previous article.
        OrdersEntity orders = modelMapper.map(orderRequestDto,OrdersEntity.class);

        for(OrderItemsEntity orderItem: orders.getItems()){ //OrderItemsEntity is already created inside the previous article.
            orderItem.setOrder(orders);
        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED); //OrderStatus is already created inside the previous article.

        OrdersEntity savedOrder = orderRepo.save(orders);
        return  modelMapper.map(savedOrder,OrderRequestDto.class);
    }

    public OrderRequestDto createOrderFallback(OrderRequestDto orderRequestDto ,Throwable throwable) {
        log.error("Fallback occured due to : {}", throwable.getMessage());

        return new OrderRequestDto();
    }
}