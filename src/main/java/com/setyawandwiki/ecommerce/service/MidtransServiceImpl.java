package com.setyawandwiki.ecommerce.service;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.Midtrans;
import com.midtrans.httpclient.SnapApi;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransSnapApi;
import com.setyawandwiki.ecommerce.dto.MidtransResponse;
import com.setyawandwiki.ecommerce.dto.OrderItemResponse;
import com.setyawandwiki.ecommerce.entity.Address;
import com.setyawandwiki.ecommerce.entity.Order;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MidtransServiceImpl implements MidtransService{
    @Value("${midtrans.serverKey}")
    private String serverKey;

    @Value("${midtrans.clientKey}")
    private String clientKey;
    private final UserRepository userRepository;
    @Override
    public MidtransResponse createSnapTransaction(Double order, List<OrderItemResponse> orderItemResponses) {
        Midtrans.clientKey = clientKey;
        Midtrans.serverKey = serverKey;
        Midtrans.isProduction = false;
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.putAll(initDataMock(order, orderItemResponses));;
        // redirectURL get url redirect to API with createTransactionRedirectUrl() method, with return String url redirect
        try {
            String transactionToken = SnapApi.createTransactionToken(requestBody);
            return MidtransResponse.builder()
                    .token(transactionToken)
                    .redirectUrl("https://app.sandbox.midtrans.com/snap/v3/redirection/" + transactionToken)
                    .build();
        } catch (MidtransError e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> initDataMock(Double order, List<OrderItemResponse> orderItemResponses) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Map<String, String> transDetail = new HashMap<>();
        transDetail.put("order_id", "SteezeClothesStore" + timestamp.getTime());
        transDetail.put("gross_amount", order.toString());
        List<Map<String, String>> items = new ArrayList<>();
        for (OrderItemResponse obj : orderItemResponses) {
            Map<String, String> map = new HashMap<>();
            map.put("id", obj.getId().toString());
            map.put("price", obj.getPrice().toString());
            map.put("quantity", obj.getQuantity().toString());
            map.put("name", obj.getProduct().getName());
            map.put("category", obj.getProduct().getCategory().getName());
            map.put("seller", obj.getProduct().getUser().getEmail());
            items.add(map);
        }
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        List<Address> addresses = user.getAddresses().stream().filter(address -> address.getIsActive().equals(true)).collect(Collectors.toList());
        Map<String, Object> billingAddres = new HashMap<>();
        billingAddres.put("first_name", user.getFirstName());
        billingAddres.put("last_name", user.getLastName());
        billingAddres.put("email", user.getEmail());
        if(addresses.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "address not found");
        }
        billingAddres.put("phone", addresses.get(0).getPhone());
        billingAddres.put("city", addresses.get(0).getStreet());
        billingAddres.put("postal_code", addresses.get(0).getPostalCode());
        billingAddres.put("country_code", "IDN");

        Map<String, Object> custDetail = new HashMap<>();
        custDetail.put("first_name", user.getFirstName());
        custDetail.put("last_name", user.getLastName());
        custDetail.put("email", user.getEmail());
        custDetail.put("phone", addresses.get(0).getStreet());
        custDetail.put("billing_address", billingAddres);
        Map<String, Object> body = new HashMap<>();

        Map<String, String> creditCard = new HashMap<>();
        creditCard.put("secure", "true");

        body.put("transaction_details", transDetail);
        body.put("item_details", items);
        body.put("customer_details", custDetail);
        body.put("credit_card", creditCard);
        return body;
    }
}
