package com.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsernameUpdateMessageDto implements Serializable {

    private String previousName;
    private String updatedName;
}
