package com.blogapp.post.entity;

import lombok.Data;

@Data
public class UpdateNicknameDto {
    private Long id;

    private String oldNickname;
    private String newNickname;
}
