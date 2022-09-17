package com.userservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.userservice.dto.group.OnCreate;
import com.userservice.dto.group.OnLogin;
import com.userservice.dto.group.OnUpdate;
import com.userservice.dto.validation.strings.StringItem;
import com.userservice.dto.validation.strings.ValidateString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @NotBlank(message = "{username} {not_blank}", groups = {OnCreate.class})
    @ValidateString(message = "{username} {invalid}", value = StringItem.USERNAME, groups = {OnCreate.class, OnUpdate.class})
    @Null(message = "{username} {null}", groups = {OnLogin.class})
    private String userName;

    @NotBlank(message = "{firstname} {not_blank}", groups = {OnCreate.class})
    @ValidateString(message = "{firstname} {invalid}", value = StringItem.FIRSTNAME, groups = {OnCreate.class, OnUpdate.class})
    @Null(message = "{firstname} {null}", groups = {OnLogin.class})
    private String firstName;

    @NotBlank(message = "{lastname} {not_blank}", groups = {OnCreate.class})
    @ValidateString(message = "{lastname} {invalid}", value = StringItem.LASTNAME, groups = {OnCreate.class, OnUpdate.class})
    @Null(message = "{lastname} {null}", groups = {OnLogin.class})
    private String lastName;

    @NotBlank(message = "{number} {not_blank}", groups = {OnCreate.class})
    @ValidateString(message = "{username} {invalid}", value = StringItem.NUMBER, groups = {OnCreate.class, OnUpdate.class})
    @Null(message = "{username} {null}", groups = {OnLogin.class})
    private String number;

    @Email(message = "{email} {invalid}", groups = {OnCreate.class, OnLogin.class, OnUpdate.class})
    @NotBlank(message = "{email} {not_blank}", groups = {OnCreate.class, OnLogin.class})
    private String email;

    @NotBlank(message = "{password} {not_blank}", groups = {OnCreate.class, OnUpdate.class, OnLogin.class})
    @ValidateString(value = StringItem.PASSWORD, message = "{password} {invalid}", groups = {OnCreate.class, OnLogin.class})
    @Null(message = "{password} {null}", groups = OnUpdate.class)
    private String password;
}
