package com.userservice.bean;

import com.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TotalUsersInfoContributor implements InfoContributor {

    private final UserRepository userRepository;

    @Override
    public void contribute(Info.Builder builder) {
        Integer users = userRepository.findAll().size();
        builder.withDetail("users", users);
    }
}
