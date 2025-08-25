package com.blogapp.post.service.client;

import com.blogapp.post.dto.CommentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class CommentFallback implements CommentFeignClient {
    @Override
    public ResponseEntity<List<CommentDto>> getComments(Long postId) {
        log.warn("Invoked fallback method of getComments");
        return null;
    }
}
