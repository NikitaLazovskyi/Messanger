package com.userservice.bean;

import com.userservice.dto.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@NoArgsConstructor
@Component
@SessionScope
public class Session {
    private UserDto user;

    public boolean isLogged() {
        return user != null;
    }
}
