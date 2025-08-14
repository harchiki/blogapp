package com.blogapp.comment.dto;

import lombok.Data;

@Data
public class CommentDto {

    private long id;

    private long writerId;

    private long postId;

    private String content;
}
