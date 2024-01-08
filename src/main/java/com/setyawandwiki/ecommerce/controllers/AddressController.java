package com.setyawandwiki.ecommerce.controllers;

import com.setyawandwiki.ecommerce.dto.AddressRequest;
import com.setyawandwiki.ecommerce.dto.AddressResponse;
import com.setyawandwiki.ecommerce.dto.WebResponse;
import com.setyawandwiki.ecommerce.entity.Address;
import com.setyawandwiki.ecommerce.service.AddressService;
import com.setyawandwiki.ecommerce.service.AddressServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/v1")
public class AddressController {
    private final AddressService addressService;
    @PostMapping(path = "/address")
    public ResponseEntity<WebResponse<AddressResponse>> create(@RequestBody AddressRequest request){
        AddressResponse address = addressService.createAddress(request);
        return ResponseEntity.ok().body(WebResponse.<AddressResponse>builder().data(address).build());
    }
    @PatchMapping(path = "/address/{id}")
    public ResponseEntity<WebResponse<AddressResponse>> update(@PathVariable("id") Long id, @RequestBody AddressRequest addressRequest){
        AddressResponse addressResponse = addressService.updateAddressById(id, addressRequest);
        return ResponseEntity.ok().body(WebResponse.<AddressResponse>builder().data(addressResponse).build());
    }
    @DeleteMapping(path = "/address/{id}")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable("id") Long id){
        addressService.deleteAddressById(id);
        return ResponseEntity.ok().body(WebResponse.<String>builder().data("success delete address").build());
    }
}
