package com.messageservice.entity;

import com.messageservice.entity.enums.ReceiverType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueUIDAndUsername", columnNames = {"uid","username"})})
public class Message {
    @Id
    private final UUID uid = UUID.randomUUID();
    private String username;
    private String message;
    private Long roomId;
    private Timestamp timestamp;
}
