package com.blogapp.post.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostDetailDto {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private long writerId;

    private List<CommentDto> comments;
}
