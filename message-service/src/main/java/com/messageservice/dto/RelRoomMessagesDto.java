package com.messageservice.dto;

import com.messageservice.entity.Message;
import com.messageservice.entity.Room;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RelRoomMessagesDto implements Serializable {
    private Room room;
    private Message message;
}
