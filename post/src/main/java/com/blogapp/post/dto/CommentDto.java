package com.blogapp.post.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

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
