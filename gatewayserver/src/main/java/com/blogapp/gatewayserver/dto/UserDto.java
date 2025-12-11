package com.blogapp.gatewayserver.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;

    private String nickname;

    private String email;
}
