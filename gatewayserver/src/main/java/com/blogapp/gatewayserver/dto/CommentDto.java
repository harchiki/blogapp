package com.blogapp.gatewayserver.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private long id;

    private String writerNickname;

    private long postId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
