package com.setyawandwiki.ecommerce.service;

import com.setyawandwiki.ecommerce.dto.AddressRequest;
import com.setyawandwiki.ecommerce.dto.AddressResponse;
import com.setyawandwiki.ecommerce.entity.Address;
import com.setyawandwiki.ecommerce.entity.User;
import com.setyawandwiki.ecommerce.repository.AddressRepository;
import com.setyawandwiki.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final ValidationService validationService;
    @Override
    public AddressResponse createAddress(AddressRequest request) {
        validationService.validate(request);
        Address address = new Address();
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        address.setPhone(request.getPhone());
        address.setCity(request.getPhone());
        address.setProvince(request.getProvince());
        address.setPostalCode(request.getPostalCode());
        address.setStreet(request.getStreet());
        address.setSubdistrict(request.getSubdistrict());
        if(Objects.isNull(request.getIsActive())){
            address.setIsActive(false);
        }else if(request.getIsActive().equals(true)){
            user.getAddresses().forEach(address1->{
                address1.setIsActive(false);
            });
            address.setIsActive(request.getIsActive());
        }else{
            address.setIsActive(false);
        }
        address.setUser(user);
        addressRepository.save(address);
        return AddressResponse.builder()
                .id(address.getId())
                .phone(address.getPhone())
                .province(address.getProvince())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .postalCode(address.getPostalCode())
                .subdistrict(address.getSubdistrict())
                .isActive(address.getIsActive())
                .build();
    }

    @Override
    public AddressResponse updateAddressById(Long id, AddressRequest request) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "address not found"));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        if(Objects.nonNull(request.getCity())){
            address.setCity(request.getCity());
        }
        if(Objects.nonNull(request.getPhone())){
            address.setPhone(request.getPhone());
        }
        if(Objects.nonNull(request.getPhone())){
            address.setCity(request.getPhone());
        }
        if(Objects.nonNull(request.getProvince())){
            address.setProvince(request.getProvince());
        }
        if(Objects.nonNull(request.getPostalCode())){
            address.setPostalCode(request.getPostalCode());
        }
        if(Objects.nonNull(request.getStreet())){
            address.setStreet(request.getStreet());
        }
        if(Objects.nonNull(request.getSubdistrict())){
            address.setSubdistrict(request.getSubdistrict());
        }
        if(Objects.isNull(request.getIsActive())){
            address.setIsActive(false);
        }else if(request.getIsActive().equals(true)){
            user.getAddresses().forEach(address1->{
                address1.setIsActive(false);
            });
            address.setIsActive(request.getIsActive());
        }else{
            address.setIsActive(false);
        }
        address.setUpdatedAt(new Date());
        addressRepository.save(address);
        return AddressResponse.builder()
                .id(address.getId())
                .street(address.getStreet())
                .city(address.getCity())
                .postalCode(address.getPostalCode())
                .subdistrict(address.getSubdistrict())
                .phone(address.getPhone())
                .isActive(address.getIsActive())
                .updatedAt(address.getUpdatedAt())
                .createdAt(address.getCreatedAt())
                .province(address.getProvince())
                .build();
    }
    @Override
    public void deleteAddressById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "address not found"));
        addressRepository.delete(address);
    }
}
