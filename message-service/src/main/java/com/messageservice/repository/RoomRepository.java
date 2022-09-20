package com.messageservice.repository;

import com.messageservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query(nativeQuery = true, value = "SELECT count(*) FROM room_members WHERE members_id = ?1 AND room_id = ?2")
    int isMemberExist(Long memberId, Long roomId);
}
