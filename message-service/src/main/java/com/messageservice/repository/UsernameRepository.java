package com.messageservice.repository;

import com.messageservice.entity.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsernameRepository extends JpaRepository<Username, Long> {
    Optional<Username> findByUsername(String username);
}
