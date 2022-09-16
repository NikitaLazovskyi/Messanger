package com.messageservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto implements Serializable {
    private Long id;
    private UserDto creator;
    private String name;
}
