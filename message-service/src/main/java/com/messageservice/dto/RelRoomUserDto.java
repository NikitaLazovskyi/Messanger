package com.messageservice.dto;

import com.messageservice.entity.Room;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelRoomUserDto implements Serializable {
    private Room room;
    private String username;
}
