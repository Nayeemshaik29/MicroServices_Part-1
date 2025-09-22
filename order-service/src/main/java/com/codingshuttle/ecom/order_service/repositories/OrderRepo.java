package com.codingshuttle.ecom.order_service.repositories;

import com.codingshuttle.ecom.order_service.entities.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrdersEntity,Long> {
}
