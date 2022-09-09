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
@Table(name = "room_users",
        uniqueConstraints = {@UniqueConstraint(name = "UniqueEventAndListener",
                columnNames = {"uid", "username"})})
public class RelRoomUser implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    @ToString.Exclude
    private Room room;

    @Id
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RelRoomUser that = (RelRoomUser) o;
        return room != null && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
