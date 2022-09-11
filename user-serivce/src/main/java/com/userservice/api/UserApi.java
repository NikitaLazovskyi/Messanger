package com.userservice.api;

import com.userservice.dto.UserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(tags = "User management API")
@RequestMapping("/api/v1/users")
public interface UserApi {

    @ApiOperation("Create user")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    UserDto create(@RequestBody UserDto userDto);

    @ApiOperation("Update user")
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping
    UserDto update(@RequestBody UserDto userDto);

    @ApiOperation("Login user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    ResponseEntity<Void> login(@RequestBody UserDto userDto);

    @ApiOperation("Logout user")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/logout")
    ResponseEntity<Void> logout();

    @ApiOperation("User profile")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/profile")
    UserDto profile();

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{userName}")
    ResponseEntity<Void> delete(@PathVariable String userName);

    @ApiOperation("Get user by email")
    @ApiImplicitParams({@ApiImplicitParam(name = "email", paramType = "path", required = true,
            value = "User email")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/email/{email}")
    UserDto getByEmail(@PathVariable String email);

    @ApiOperation("Get user by username")
    @ApiImplicitParams({@ApiImplicitParam(name = "username", paramType = "path", required = true,
            value = "Username")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username/{userName}")
    UserDto getByUserName(@PathVariable String userName);

    @ApiOperation("Get all users")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    List<UserDto> allUsers();
}
