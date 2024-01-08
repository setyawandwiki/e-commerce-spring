package com.setyawandwiki.ecommerce.repository;

import com.setyawandwiki.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByOrderItemsUserEmail(String email);
}
