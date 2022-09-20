package com.messageservice.controller;

import com.messageservice.api.RoomApi;
import com.messageservice.dto.RoomDto;
import com.messageservice.dto.UserDto;
import com.messageservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class RoomController implements RoomApi {

    private final RoomService roomService;

    @Override
    public RoomDto create(RoomDto roomDto) {
        return roomService.create(roomDto);
    }

    @Override
    public ResponseEntity<Void> delete(Long roomId) {
        return roomService.delete(roomId);
    }

    @Override
    public RoomDto rename(RoomDto roomDto) {
        return roomService.rename(roomDto);
    }

    @Override
    public List<RoomDto> showAllRooms() {
        return roomService.showAllRooms();
    }

    @Override
    public ResponseEntity<Void> addMember(Long roomId, String username) {
        return roomService.addMember(roomId, username);
    }

    @Override
    public ResponseEntity<Void> removeMember(Long roomId, String username) {
        return roomService.removeMember(roomId, username);
    }

    @Override
    public Set<UserDto> showMembers(Long roomId) {
        return roomService.showMembers(roomId);
    }
}
