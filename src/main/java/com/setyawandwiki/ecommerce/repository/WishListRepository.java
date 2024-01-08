package com.setyawandwiki.ecommerce.repository;

import com.setyawandwiki.ecommerce.entity.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
}
