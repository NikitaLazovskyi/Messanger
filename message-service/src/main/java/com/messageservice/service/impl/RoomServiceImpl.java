package com.messageservice.service.impl;

import com.messageservice.dto.RoomDto;
import com.messageservice.dto.UserDto;
import com.messageservice.entity.Room;
import com.messageservice.entity.User;
import com.messageservice.mapper.RoomMapper;
import com.messageservice.mapper.UsernameMapper;
import com.messageservice.repository.RoomRepository;
import com.messageservice.repository.UsernameRepository;
import com.messageservice.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final UsernameRepository usernameRepository;
    private final RoomMapper roomMapper = RoomMapper.INSTANCE;
    private final UsernameMapper usernameMapper = UsernameMapper.INSTANCE;

    @Override
    @Transactional
    public RoomDto create(RoomDto roomDto) {
        String username = roomDto.getCreator().getUsername();
        Room room = roomMapper.mapToEntity(roomDto);
        User creator = usernameRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("user with username: " + username + " doesn't exist")
        );
        room.setCreator(creator);
        room.setMembers(new HashSet<>());
        room.getMembers().add(creator);
        Room persisted = roomRepository.save(room);
        log.info("Room was created with id: {}", persisted.getId());
        return roomMapper.mapToDto(persisted);
    }

    @Override
    public ResponseEntity<Void> delete(Long roomId) {
        roomRepository.deleteById(roomId);
        log.info("Room was deleted with id: {}", roomId);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public RoomDto rename(RoomDto roomDto) {
        Room room = roomRepository.getById(roomDto.getId());
        room.setName(roomDto.getName());
        Room persisted = roomRepository.save(room);
        log.info("Room with id: {} was renamed from {} to {}", persisted.getId(), roomDto.getName(), persisted.getName());
        return roomMapper.mapToDto(persisted);
    }

    @Override
    public List<RoomDto> showAllRooms() {
        List<RoomDto> roomsDto = roomRepository.findAll().stream().map(roomMapper::mapToDto).collect(Collectors.toList());
        log.info("All rooms were requested");
        return roomsDto;
    }

    @Override
    @Transactional
    public ResponseEntity<Void> addMember(Long roomId, String username) {
        User member = usernameRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("user with username: " + username + " doesn't exist")
        );
        Room room = roomRepository.getById(roomId);
        room.getMembers().add(member);
        roomRepository.save(room);
        log.info("To members list of room with id: {} was added new member with username: {}", roomId, username);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeMember(Long roomId, String username) {
        User member = usernameRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("user with username: " + username + " doesn't exist")
        );
        Room room = roomRepository.getById(roomId);
        room.getMembers().remove(member);
        roomRepository.save(room);
        log.info("From members list of room with id: {} was removed member with username: {}", roomId, username);
        return ResponseEntity.ok().build();
    }

    @Override
    public Set<UserDto> showMembers(Long roomId) {
        Set<UserDto> members = roomRepository.getById(roomId).getMembers().stream().map(usernameMapper::mapToDto).collect(Collectors.toSet());
        log.info("Member-list was requested for room with id: {}", roomId);
        return members;
    }

    @Override
    public boolean isMember(Long memberId, Long roomId) {
        return roomRepository.isMemberExist(memberId, roomId) > 0;
    }

    @Override
    public boolean isExist(Long memberId) {
        return roomRepository.existsById(memberId);
    }
}
