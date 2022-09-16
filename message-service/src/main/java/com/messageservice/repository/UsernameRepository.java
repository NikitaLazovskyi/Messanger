package com.messageservice.repository;

import com.messageservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsernameRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
