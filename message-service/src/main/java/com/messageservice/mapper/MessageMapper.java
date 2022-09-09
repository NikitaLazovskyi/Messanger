package com.messageservice.mapper;

import com.messageservice.dto.MessageDto;
import com.messageservice.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    Message mapToEntity(MessageDto messageDto);

    MessageDto mapToDto(Message message);
}
