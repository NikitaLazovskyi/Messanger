package com.messageservice.mapper;

import com.messageservice.dto.RoomDto;
import com.messageservice.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RoomMapper {

    RoomMapper INSTANCE = Mappers.getMapper(RoomMapper.class);

    Room mapToEntity(RoomDto roomDto);

    RoomDto mapToDto(Room room);
}
