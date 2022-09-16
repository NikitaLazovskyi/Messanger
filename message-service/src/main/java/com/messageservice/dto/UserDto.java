package com.messageservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    private Long id;
    private String username;

    public UserDto(String username){
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserDto username1 = (UserDto) o;
        return username != null && Objects.equals(username, username1.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

