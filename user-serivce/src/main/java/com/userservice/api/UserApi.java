package com.userservice.api;

import com.userservice.dto.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/users")
public interface UserApi {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserDto create(@RequestBody UserDto userDto);

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    ResponseEntity<Void> update(@RequestBody UserDto userDto);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/login")
    ResponseEntity<Void> login(@RequestBody UserDto userDto);

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/logout")
    ResponseEntity<Void> logout();

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userName}")
    ResponseEntity<Void> delete(@PathVariable String userName);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email/{email}")
    UserDto getByEmail(@PathVariable String email);

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username/{userName}")
    UserDto getByUserName(@PathVariable String userName);
}
