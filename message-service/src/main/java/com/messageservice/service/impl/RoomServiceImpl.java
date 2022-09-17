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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
                ()->new EntityNotFoundException("user with username: " + username + " doesn't exist")
        );
        room.setCreator(creator);
        room.setMembers(new HashSet<>());
        room.getMembers().add(creator);
        Room persisted = roomRepository.save(room);
        return roomMapper.mapToDto(persisted);
    }

    @Override
    public ResponseEntity<Void> delete(Long roomId) {
        roomRepository.deleteById(roomId);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public RoomDto rename(RoomDto roomDto) {
        Room room = roomRepository.getById(roomDto.getId());
        room.setName(roomDto.getName());
        return roomMapper.mapToDto(roomRepository.save(room));
    }

    @Override
    public List<RoomDto> showAllRooms() {
        return roomRepository.findAll().stream().map(roomMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ResponseEntity<Void> addMember(Long roomId, String username) {
        User member = usernameRepository.findByUsername(username).orElseThrow(
                ()->new EntityNotFoundException("user with username: " + username + " doesn't exist")
        );
        Room room = roomRepository.getById(roomId);
        room.getMembers().add(member);
        roomRepository.save(room);
        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional
    public ResponseEntity<Void> removeMember(Long roomId, String username) {
        User member = usernameRepository.findByUsername(username).orElseThrow(
                ()->new EntityNotFoundException("user with username: " + username + " doesn't exist")
        );
        Room room = roomRepository.getById(roomId);
        room.getMembers().remove(member);
        roomRepository.save(room);
        return ResponseEntity.ok().build();
    }

    @Override
    public Set<UserDto> showMembers(Long roomId) {
        return roomRepository.getById(roomId).getMembers().stream().map(usernameMapper::mapToDto).collect(Collectors.toSet());
    }
}
