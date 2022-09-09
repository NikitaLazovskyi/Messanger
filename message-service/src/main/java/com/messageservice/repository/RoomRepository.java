package com.messageservice.repository;

import com.messageservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query(value = "SELECT ru.username FROM RelRoomUser ru WHERE ru.room.id = :roomId")
    List<String> getMembers(@Param("roomId") Long roomId);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO room_users (uid, username) VALUES (?1,?2)", nativeQuery = true)
    void addMember(Long roomId, String username);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM RelRoomUser ru WHERE ru.username = ?2 AND ru.room.id = ?1")
    void removeMember(Long roomId, String username);
}
