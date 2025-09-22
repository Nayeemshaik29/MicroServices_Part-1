package com.codingshuttle.ecom.inventory_service.repositories;

import com.codingshuttle.ecom.inventory_service.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {
}