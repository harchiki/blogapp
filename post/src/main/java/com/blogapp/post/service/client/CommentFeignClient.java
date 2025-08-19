package com.blogapp.post.service.client;

import com.blogapp.post.dto.CommentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("COMMENT")
public interface CommentFeignClient {
    @GetMapping(value = "/comment/by-post/{post-id}", consumes = "application/json")
    ResponseEntity<List<CommentDto>> getComments(@PathVariable("post-id") Long postId);
}
