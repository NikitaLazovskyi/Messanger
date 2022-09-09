package com.messageservice.service.impl;

import com.messageservice.dto.RoomDto;
import com.messageservice.entity.Room;
import com.messageservice.mapper.RoomMapper;
import com.messageservice.repository.RoomRepository;
import com.messageservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper mapper = RoomMapper.INSTANCE;

    @Override
    public RoomDto create(RoomDto roomDto) {
        Room persisted = roomRepository.save(mapper.mapToEntity(roomDto));
        return mapper.mapToDto(persisted);
    }

    @Override
    public ResponseEntity<Void> delete(RoomDto roomDto) {
        roomRepository.delete(mapper.mapToEntity(roomDto));
        return ResponseEntity.ok().build();
    }

    @Override
    public RoomDto rename(RoomDto roomDto) {
        Room room = roomRepository.getById(roomDto.getId());
        room.setName(roomDto.getName());
        return mapper.mapToDto(roomRepository.save(room));
    }

    @Override
    public List<RoomDto> showAllRooms() {
        return roomRepository.findAll().stream().map(mapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public ResponseEntity<Void> addMember(Long roomId, String username) {
        roomRepository.addMember(roomId, username);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> removeMember(Long roomId, String username) {
        roomRepository.removeMember(roomId, username);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<String> showMembers(Long roomId) {
        return roomRepository.getMembers(roomId);
    }
}
