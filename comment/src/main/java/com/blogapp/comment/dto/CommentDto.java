package com.blogapp.comment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {

    private long id;

    private long writerId;

    private long postId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
