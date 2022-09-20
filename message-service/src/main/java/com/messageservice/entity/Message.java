package com.messageservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueUIDAndUsername", columnNames = {"uid", "sender"})})
public class Message implements Serializable {
    @Id
    private UUID uid;
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "sender", nullable = false)
    private User sender;
    private String message;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room", nullable = false)
    private Room room;
    private Timestamp timestamp;
}
