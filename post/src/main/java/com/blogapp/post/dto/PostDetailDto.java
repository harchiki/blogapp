package com.blogapp.post.dto;

import jakarta.persistence.Column;
import lombok.Data;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailDto {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private long writerId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentDto> comments;
}
