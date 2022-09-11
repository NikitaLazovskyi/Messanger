package com.messageservice.repository;

import com.messageservice.entity.Username;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsernameRepository extends JpaRepository<Username, Long> {
    Username findByUsername(String username);
}
