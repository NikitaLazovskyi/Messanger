package com.messageservice.service;

import com.messageservice.dto.RoomDto;
import com.messageservice.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface RoomService {

    RoomDto create(RoomDto roomDto);

    ResponseEntity<Void> delete(Long roomId);

    RoomDto rename(RoomDto roomDto);

    List<RoomDto> showAllRooms();

    ResponseEntity<Void> addMember(Long roomId, String username);

    ResponseEntity<Void> removeMember(Long roomId, String username);

    Set<UserDto> showMembers(Long roomId);
}
