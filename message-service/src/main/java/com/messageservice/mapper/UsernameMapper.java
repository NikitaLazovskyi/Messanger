package com.messageservice.mapper;

import com.messageservice.dto.MessageDto;
import com.messageservice.dto.UsernameDto;
import com.messageservice.entity.Message;
import com.messageservice.entity.Username;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UsernameMapper {

    UsernameMapper INSTANCE = Mappers.getMapper(UsernameMapper.class);

    Username mapToEntity(UsernameDto usernameDto);

    UsernameDto mapToDto(Username username);
}
