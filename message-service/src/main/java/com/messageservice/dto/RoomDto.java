package com.messageservice.dto;

import com.messageservice.dto.group.OnCreateRoom;
import com.messageservice.dto.group.OnRenameRoom;
import com.messageservice.dto.group.OnSendMessage;
import com.messageservice.dto.strings.StringItem;
import com.messageservice.dto.strings.ValidateString;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomDto implements Serializable {

    @Positive(message = "{id} {of_room} {positive}", groups = {OnRenameRoom.class, OnSendMessage.class})
    @Null(message = "{id} {of_room} {null}", groups = OnCreateRoom.class)
    private Long id;

    @NotNull(message = "{creator} {of_room} {not_null}", groups = OnCreateRoom.class)
    @Null(message = "{creator} {of_room} {null}", groups = {OnRenameRoom.class, OnSendMessage.class})
    @Valid
    private UserDto creator;

    @NotBlank(message = "{room_name} {not_blank}", groups = {OnCreateRoom.class, OnRenameRoom.class})
    @Null(message = "{room_name} {null}", groups = {OnSendMessage.class})
    @ValidateString(value = StringItem.ROOM_NAME, message = "{room_name} {invalid}", groups = {OnCreateRoom.class, OnRenameRoom.class})
    private String name;
}
