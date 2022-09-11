package com.userservice.mapper;

import com.userservice.dto.UserDto;
import com.userservice.entity.User;
import com.userservice.exception.InvalidOperationException;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapUser(UserDto userDto);

    UserDto mapUserDto(User user);

    default void updateUserFieldsWithDto(UserDto userDto, User user){
        boolean usernameKey = user.getUserName().equals(userDto.getUserName());
        boolean emailKey = user.getEmail().equals(userDto.getEmail());

        //identifiers
        if (usernameKey && Objects.nonNull(userDto.getEmail()))
            user.setEmail(userDto.getEmail());
        if (emailKey && Objects.nonNull(userDto.getUserName()))
            user.setUserName(userDto.getUserName());

        //info fields
        if (Objects.nonNull(userDto.getFirstName()))
            user.setFirstName(userDto.getFirstName());
        if (Objects.nonNull(userDto.getLastName()))
            user.setLastName(userDto.getLastName());
        if (Objects.nonNull(userDto.getNumber()))
            user.setNumber(userDto.getNumber());
    }
}

