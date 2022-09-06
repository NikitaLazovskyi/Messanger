package com.userservice.mapper;

import com.userservice.dto.UserDto;
import com.userservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapUser(UserDto userDto);

    UserDto mapUserDto(User user);

    default void updateUserFieldsWithDto(UserDto userDto, User user){
        if (Objects.nonNull(userDto.getFirstName()))
            user.setFirstName(userDto.getFirstName());
        if (Objects.nonNull(userDto.getLastName()))
            user.setLastName(userDto.getLastName());
        if (Objects.nonNull(userDto.getNumber()))
            user.setNumber(userDto.getNumber());
        if (Objects.nonNull(userDto.getEmail()))
            user.setEmail(userDto.getEmail());
    }
}

