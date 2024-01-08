package com.setyawandwiki.ecommerce.repository;

import com.setyawandwiki.ecommerce.entity.Role;
import com.setyawandwiki.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByToken(String token);
    User findByRole(Role role);
}
