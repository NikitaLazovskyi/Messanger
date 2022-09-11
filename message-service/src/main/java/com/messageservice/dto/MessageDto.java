package com.messageservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.messageservice.entity.Room;
import com.messageservice.entity.Username;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.ReadOnlyProperty;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    private UUID uid;
    private UsernameDto sender;
    private RoomDto room;
    private String message;
    private Timestamp timestamp;
}
