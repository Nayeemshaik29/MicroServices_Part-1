package com.codingshuttle.ecom.inventory_service.services;

import com.codingshuttle.ecom.inventory_service.dtos.OrderRequestDto;
import com.codingshuttle.ecom.inventory_service.dtos.OrderRequestItemDto;
import com.codingshuttle.ecom.inventory_service.dtos.ProductDto;
import com.codingshuttle.ecom.inventory_service.entities.ProductEntity;
import com.codingshuttle.ecom.inventory_service.repositories.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;
    private final ModelMapper modelMapper;


    public List<ProductDto> getAllProducts() {
        log.info("Fetching all inventory items");
        List<ProductEntity> inventories = productRepo.findAll();
        return inventories.stream()
                .map(productEntity -> modelMapper.map(productEntity,ProductDto.class))
                //.collect(Collectors.toList());
                .toList();
    }

    public ProductDto getProductById(Long id) {
        log.info("Fetching Product with Id: {}",id);
        Optional<ProductEntity> inventory = productRepo.findById(id);
        return inventory.map(item -> modelMapper.map(item,ProductDto.class))
                .orElseThrow(()->new RuntimeException("Inventory not found"));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing the stocks");
        Double totalPrice = 0.0;
        for(OrderRequestItemDto orderRequestItemDto: orderRequestDto.getItems())
        {
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();

            ProductEntity product = productRepo.findById(productId).orElseThrow(()->
                    new RuntimeException("Product not found with id" +productId));

            if(product.getStock() < quantity)
            {
                throw new RuntimeException("Product cannot be fullfilled for given qunatity.");
            }
            product.setStock(product.getStock()- quantity);
            productRepo.save(product);
            totalPrice += quantity*product.getPrice();





        }
        return totalPrice;
    }
}
