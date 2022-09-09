package com.messageservice.service;

import com.messageservice.dto.RoomDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RoomService {

    RoomDto create(RoomDto roomDto);

    ResponseEntity<Void> delete(RoomDto roomDto);

    RoomDto rename(RoomDto roomDto);

    List<RoomDto> showAllRooms();

    ResponseEntity<Void> addMember(Long roomId, String username);

    ResponseEntity<Void> removeMember(Long roomId, String username);

    List<String> showMembers(Long roomId);
}
