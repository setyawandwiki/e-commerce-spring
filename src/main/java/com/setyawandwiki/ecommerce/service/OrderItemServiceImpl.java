package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.OrderItemRequest;
import com.setyawandwiki.ecommerce.dto.OrderItemResponse;
import com.setyawandwiki.ecommerce.dto.OrderItemUpdateRequest;
import com.setyawandwiki.ecommerce.entity.OrderItem;
import com.setyawandwiki.ecommerce.entity.Product;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.OrderItemRepository;
import com.setyawandwiki.ecommerce.repository.ProductRepository;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService{
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Override
    public List<OrderItemResponse> getAllOrderItem() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderItem> byProductUserEmail = orderItemRepository.findByUserEmail(email);
        List<OrderItemResponse> productResponses = byProductUserEmail.stream()
                .filter(orderItem -> orderItem.getStatus().equals("process"))
                .map(orderItem -> OrderItemResponse.builder()
                        .product(orderItem.getProduct())
                        .id(orderItem.getId())
                        .price(orderItem.getPrice())
                        .updatedAt(orderItem.getUpdatedAt())
                        .createdAt(orderItem.getCreatedAt())
                        .quantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());
        return productResponses;
    }

    @Override
    public List<OrderItemResponse> getAllOrderItemUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<OrderItem> byProductUserEmail = orderItemRepository.findByUserEmail(email);
        byProductUserEmail.stream().filter(product-> product.getStatus() != "process");
        List<OrderItemResponse> productResponses = byProductUserEmail.stream()
                .filter(orderItem -> orderItem.getStatus().equals("success"))
                .map(orderItem -> OrderItemResponse.builder()
                        .product(orderItem.getProduct())
                        .id(orderItem.getId())
                        .price(orderItem.getPrice())
                        .updatedAt(orderItem.getUpdatedAt())
                        .createdAt(orderItem.getCreatedAt())
                        .quantity(orderItem.getQuantity())
                        .build())
                .collect(Collectors.toList());
        return productResponses;
    }

    @Override
    public OrderItemResponse addOrderItem(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(1);
        orderItem.setPrice(product.getPrice());
        orderItem.setUser(user);
        orderItem.setProduct(product);
        orderItem.setStatus("process");
        orderItemRepository.save(orderItem);
        return OrderItemResponse.builder()
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .product(orderItem.getProduct())
                .build();
    }

    @Override
    public OrderItemResponse updateOrderItem(OrderItemUpdateRequest request, Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order item not found"));
        if(Objects.nonNull(request.getQuantity())){
            orderItem.setQuantity(request.getQuantity() );
            orderItem.setPrice(orderItem.getProduct().getPrice() * request.getQuantity());
        }
        OrderItem orderItem1 = orderItemRepository.save(orderItem);
        return OrderItemResponse.builder()
                .id(orderItem1.getId())
                .product(orderItem1.getProduct())
                .quantity(orderItem1.getQuantity())
                .price(orderItem1.getPrice())
                .updatedAt(new Date(System.currentTimeMillis()))
                .build();
    }

    @Override
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order item not found"));
        orderItemRepository.delete(orderItem);
    }
}
