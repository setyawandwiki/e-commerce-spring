package com.setyawandwiki.ecommerce.repository;

import com.setyawandwiki.ecommerce.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByUserEmail(String email);
}
