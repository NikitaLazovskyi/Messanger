package com.messageservice.mapper;

import com.messageservice.dto.MessageDto;
import com.messageservice.dto.RoomDto;
import com.messageservice.dto.UserDto;
import com.messageservice.entity.Message;
import com.messageservice.entity.Room;
import com.messageservice.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message mapToEntity(MessageDto messageDto);

    MessageDto mapToDto(Message message);
}
