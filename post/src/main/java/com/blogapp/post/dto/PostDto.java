package com.blogapp.post.dto;

import lombok.Data;

@Data
public class PostDto {
    private Long id;

    private String title;

    private String summary;

    private String content;

    private long writerId;
}
