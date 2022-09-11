package com.messageservice.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_messages",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueEventAndListener",
                columnNames = {"roomId", "messageUid"})})
public class RelRoomMessages implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roomId", nullable = false)
    @ToString.Exclude
    private Room room;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "messageUid", nullable = false)
    @ToString.Exclude
    private Message message;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RelRoomMessages that = (RelRoomMessages) o;
        return room != null && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
