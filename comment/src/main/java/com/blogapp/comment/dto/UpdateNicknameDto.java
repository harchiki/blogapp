package com.blogapp.comment.dto;

import lombok.Data;

@Data
public class UpdateNicknameDto {
    private Long id;

    private String oldNickname;
    private String newNickname;
}
