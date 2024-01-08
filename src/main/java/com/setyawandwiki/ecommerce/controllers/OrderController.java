package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.MidtransResponse;
import com.setyawandwiki.ecommerce.dto.OrderResponse;
import com.setyawandwiki.ecommerce.dto.WebResponse;
import com.setyawandwiki.ecommerce.entity.Order;
import com.setyawandwiki.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/v1/order")
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public ResponseEntity<WebResponse<MidtransResponse>> addOrder(){
        MidtransResponse midtransResponse = orderService.addOrder();
        return ResponseEntity.ok().body(WebResponse.<MidtransResponse>builder()
                        .data(midtransResponse)
                .build());
    }
    @GetMapping
    public ResponseEntity<WebResponse<List<OrderResponse>>> getAllOrder(){
        List<OrderResponse> allOrder = orderService.getAllOrder();
        return ResponseEntity.ok().body(WebResponse.<List<OrderResponse>>builder().data(allOrder).build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<WebResponse<String>> deleteOrder(@PathVariable("id") Long id){
        orderService.deleteByid(id);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data("order success deleted").build());
    }
}
