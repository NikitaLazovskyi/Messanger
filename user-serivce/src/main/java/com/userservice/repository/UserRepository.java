package com.userservice.repository;

import com.userservice.entity.User;
import com.userservice.repository.impl.enums.SelectUser;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    List<User> findAll();

    ResponseEntity<Void> delete(User user);

    User insert(User user);

    boolean exists(User user);

    User update(User user, SelectUser by);

    Optional<User> findBy(String obj, SelectUser by);
}
