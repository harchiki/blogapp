package com.blogapp.user.function;

import com.blogapp.user.dto.UpdateNicknameDto;
import com.blogapp.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class UserFunctions {
    @Bean
    public Consumer<UpdateNicknameDto> rollbackUserNickname(UserService userService) {
        return updateNickname -> {
            log.info("Received rollbackUserNickname request for the details: {}", updateNickname);
            userService.rollbackUserNickname(updateNickname);
        };
    }
}
