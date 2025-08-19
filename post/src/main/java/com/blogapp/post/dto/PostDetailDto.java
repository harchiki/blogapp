package com.blogapp.post.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDetailDto {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private AccountDto writer;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<CommentDto> comments;
}
