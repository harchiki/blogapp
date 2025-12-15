package com.blogapp.user.dto;

import lombok.Data;

@Data
public class UpdateNicknameDto {
    private String oldNickname;
    private String newNickname;
}
