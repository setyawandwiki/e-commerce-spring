package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.MidtransResponse;
import com.setyawandwiki.ecommerce.dto.OrderItemResponse;
import com.setyawandwiki.ecommerce.dto.OrderResponse;
import com.setyawandwiki.ecommerce.entity.Order;
import com.setyawandwiki.ecommerce.entity.OrderItem;
import com.setyawandwiki.ecommerce.entity.Product;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.OrderItemRepository;
import com.setyawandwiki.ecommerce.repository.OrderRepository;
import com.setyawandwiki.ecommerce.repository.ProductRepository;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final MidtransService midtransService;
    private final OrderItemService orderItemService;
    private Double total = 0.0;
    private Boolean isREaadyToOrder = false;
    @Override
    public MidtransResponse addOrder() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        if(user.getAddresses().size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "you are not fill the address yet");
        }
        List<OrderItem> orderItems = orderItemRepository.findByUserEmail(email).stream().filter(order->order.getStatus().equals("process")).collect(Collectors.toList());
        orderItems.forEach(orderItem -> {
            isREaadyToOrder = false;
            if(orderItem.getStatus().equals("process")){
                isREaadyToOrder = true;
                return;
            }
        });
        if(isREaadyToOrder.equals(false)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "there is no order");
        }else{
            orderItems.forEach(orderItem->{
                if(orderItem.getStatus().equals("process")){
                    total += orderItem.getPrice();
                }
            });
            Order order = new Order();
            order.setUser(user);
            List<OrderItemResponse> allOrderItem = orderItemService.getAllOrderItem();
            orderItems.forEach(orderItem->{
                if(orderItem.getStatus().equals("process")) {
                    Product product = productRepository.findById(orderItem.getProduct().getId()).get();
                    if(product.getQuantity() - orderItem.getQuantity() < 0){
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "quantity products out of limit");
                    }
                    product.setQuantity(product.getQuantity() - orderItem.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setStatus("success");
                    orderItemRepository.save(orderItem);
                }
            });
            MidtransResponse snapTransaction = midtransService.createSnapTransaction(total + 29000, allOrderItem);
            orderRepository.save(order);
            isREaadyToOrder = false;
            total = 0.0;
            return snapTransaction;
        }
    }

    @Override
    public List<OrderResponse> getAllOrder() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Order> byOrderItemUserEmail = orderRepository.findByOrderItemsUserEmail(email);
        List<OrderResponse> orderResponses = byOrderItemUserEmail.stream().map(order ->
                OrderResponse.builder()
                        .user(order.getUser())
                        .price(order.getPrice())
                        .id(order.getId())
                        .build()).collect(Collectors.toList());
        return orderResponses;
    }

    @Override
    public void deleteByid(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found"));
        orderRepository.delete(order);
    }
}
