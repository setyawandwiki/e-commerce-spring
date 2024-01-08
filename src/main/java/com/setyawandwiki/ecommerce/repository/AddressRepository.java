package com.setyawandwiki.ecommerce.repository;

import com.setyawandwiki.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
