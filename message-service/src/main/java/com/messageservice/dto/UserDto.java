package com.messageservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.messageservice.dto.group.OnCreateRoom;
import com.messageservice.dto.group.OnSendMessage;
import com.messageservice.dto.strings.StringItem;
import com.messageservice.dto.strings.ValidateString;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Null(message = "{id} {of_username} {null}", groups = {OnSendMessage.class, OnCreateRoom.class})
    private Long id;

    @ValidateString(value = StringItem.USERNAME, message = "{username} {invalid}", groups = {OnSendMessage.class, OnCreateRoom.class})
    @NotNull(message = "{username} {not_null}", groups = {OnSendMessage.class, OnCreateRoom.class})
    private String username;

    public UserDto(String username) {
        this.username = username;
    }
}

