package com.messageservice.repository;

import com.messageservice.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {

    @Query("SELECT msg FROM Message msg WHERE msg.room.id = ?1")
    List<Message> findAllByRoomId(Long roomId);
}
