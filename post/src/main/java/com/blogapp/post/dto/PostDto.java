package com.blogapp.post.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class PostDto {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private long writerId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
