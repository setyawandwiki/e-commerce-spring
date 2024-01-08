package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.MidtransResponse;
import com.setyawandwiki.ecommerce.dto.OrderItemResponse;
import com.setyawandwiki.ecommerce.dto.OrderResponse;
import com.setyawandwiki.ecommerce.entity.Order;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface MidtransService {
    MidtransResponse createSnapTransaction(Double order, List<OrderItemResponse> orderItemResponses);
    Map<String, Object> initDataMock(Double order, List<OrderItemResponse> orderItemResponses);
}
