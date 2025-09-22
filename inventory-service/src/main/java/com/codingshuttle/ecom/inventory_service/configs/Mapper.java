package com.codingshuttle.ecom.inventory_service.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Mapper {

    @Bean
    public ModelMapper getMapper(){
        return new ModelMapper();
    }
}