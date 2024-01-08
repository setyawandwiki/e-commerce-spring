package com.setyawandwiki.ecommerce.repository;

import com.setyawandwiki.ecommerce.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
