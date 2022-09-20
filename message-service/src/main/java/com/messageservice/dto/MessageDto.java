package com.messageservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.messageservice.dto.group.OnSendMessage;
import com.messageservice.dto.strings.StringItem;
import com.messageservice.dto.strings.ValidateString;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID uid;

    @NotNull(message = "{sender} {not_null}", groups = OnSendMessage.class)
    @Valid
    private UserDto sender;

    @NotNull(message = "{room} {not_null}", groups = OnSendMessage.class )
    @Valid
    private RoomDto room;

    @NotBlank(message = "{message} {not_blank}", groups = OnSendMessage.class)
    @ValidateString(value = StringItem.MESSAGE, message = "{message} {invalid}", groups = OnSendMessage.class)
    private String message;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp timestamp;
}
