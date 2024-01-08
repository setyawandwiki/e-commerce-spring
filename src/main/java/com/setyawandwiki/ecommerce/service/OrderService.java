package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.MidtransResponse;
import com.setyawandwiki.ecommerce.dto.OrderResponse;
import com.setyawandwiki.ecommerce.entity.Order;

import java.util.List;

public interface OrderService {
    MidtransResponse addOrder();
    List<OrderResponse> getAllOrder();
    void deleteByid(Long id);
}
