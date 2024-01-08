package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.AddressRequest;
import com.setyawandwiki.ecommerce.dto.AddressResponse;
import com.setyawandwiki.ecommerce.entity.Address;

import java.util.List;

public interface AddressService {
    AddressResponse createAddress(AddressRequest request);
    AddressResponse updateAddressById(Long id, AddressRequest request);
    void deleteAddressById(Long id);
}
