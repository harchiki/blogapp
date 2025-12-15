package com.blogapp.comment.function;

import com.blogapp.comment.dto.UpdateNicknameDto;
import com.blogapp.comment.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class CommentFunctions {
    @Bean
    public Consumer<UpdateNicknameDto> updateCommentNickname(CommentService commentService) {
        return updateNickname -> {
            log.info("Received UpdateNicknameDto request for the details: {}", updateNickname);
            commentService.updateCommentNickname(updateNickname);
        };
    }
}
