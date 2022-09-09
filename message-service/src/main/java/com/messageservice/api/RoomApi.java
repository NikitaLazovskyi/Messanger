package com.messageservice.api;

import com.messageservice.dto.RoomDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/room")
public interface RoomApi {
    @PostMapping
    RoomDto create(@RequestBody RoomDto roomDto);

    @DeleteMapping
    ResponseEntity<Void> delete(@RequestBody RoomDto roomDto);

    @PatchMapping
    RoomDto rename(@RequestBody RoomDto roomDto);

    @GetMapping
    List<RoomDto> showAllRooms();

    @PutMapping("/add-member")
    ResponseEntity<Void> addMember(@RequestParam("roomId") Long roomId, @RequestParam("username") String username);

    @DeleteMapping("/remove-member")
    ResponseEntity<Void> removeMember(@RequestParam("roomId") Long roomId, @RequestParam("username") String username);

    @GetMapping("/members/{roomId}")
    List<String> showMembers(@PathVariable("roomId") Long roomId);
}
