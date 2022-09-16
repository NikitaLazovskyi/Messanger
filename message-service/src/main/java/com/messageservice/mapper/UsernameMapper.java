package com.messageservice.mapper;

import com.messageservice.dto.UserDto;
import com.messageservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsernameMapper {

    UsernameMapper INSTANCE = Mappers.getMapper(UsernameMapper.class);

    User mapToEntity(UserDto userDto);

    UserDto mapToDto(User user);
}
