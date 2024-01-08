package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.OrderItemRequest;
import com.setyawandwiki.ecommerce.dto.OrderItemResponse;
import com.setyawandwiki.ecommerce.dto.OrderItemUpdateRequest;
import com.setyawandwiki.ecommerce.dto.WebResponse;
import com.setyawandwiki.ecommerce.entity.Order;
import com.setyawandwiki.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1/orderItem")
public class OrderItemController {
    private final OrderItemService orderItemService;
    @GetMapping
    public ResponseEntity<WebResponse<List<OrderItemResponse>>> getAll(){
        List<OrderItemResponse> allOrderItem = orderItemService.getAllOrderItem();
        return ResponseEntity.ok().body(WebResponse.<List<OrderItemResponse>>builder().data(allOrderItem).build());
    }
    @GetMapping("/user")
    public ResponseEntity<WebResponse<List<OrderItemResponse>>> getAllItemUSer(){
        List<OrderItemResponse> allOrderItem = orderItemService.getAllOrderItemUser();
        return ResponseEntity.ok().body(WebResponse.<List<OrderItemResponse>>builder().data(allOrderItem).build());
    }
    @PostMapping(path = "/{id}")
    public ResponseEntity<WebResponse<OrderItemResponse>> addOrderItem(@PathVariable("id") Long id){
        OrderItemResponse orderItemResponse = orderItemService.addOrderItem(id);
        return ResponseEntity.ok().body(WebResponse.<OrderItemResponse>builder().data(orderItemResponse).build());
    }
    @PatchMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WebResponse<OrderItemResponse>> updateOrderItem(@RequestBody OrderItemUpdateRequest request, @PathVariable("id") Long id){
        OrderItemResponse orderItemResponse = orderItemService.updateOrderItem(request, id);
        return ResponseEntity.ok().body(WebResponse.<OrderItemResponse>builder().data(orderItemResponse).build());
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<WebResponse<String>> deleteOrderItem(@PathVariable("id") Long id){
        orderItemService.deleteOrderItem(id);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data("success delete order item").build());
    }
}
