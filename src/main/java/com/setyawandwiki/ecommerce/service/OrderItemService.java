package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.OrderItemResponse;
import com.setyawandwiki.ecommerce.dto.OrderItemUpdateRequest;

import java.util.List;

public interface OrderItemService {
    List<OrderItemResponse> getAllOrderItem();
    List<OrderItemResponse> getAllOrderItemUser();
    OrderItemResponse addOrderItem(Long id);
    OrderItemResponse updateOrderItem(OrderItemUpdateRequest request, Long id);
    void deleteOrderItem(Long id);
}
