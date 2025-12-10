package com.blogapp.post.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
