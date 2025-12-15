package com.blogapp.post.function;

import com.blogapp.post.entity.UpdateNicknameDto;
import com.blogapp.post.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class PostFunctions {
    @Bean
    public Consumer<UpdateNicknameDto> updatePostNickname(PostService postService) {
        return updateNickname -> {
            log.info("Received UpdateNicknameDto request for the details: {}", updateNickname);
            postService.updatePostNickname(updateNickname);
        };
    }
}
