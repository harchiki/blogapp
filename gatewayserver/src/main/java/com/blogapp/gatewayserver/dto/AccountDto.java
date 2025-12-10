package com.blogapp.gatewayserver.dto;

import lombok.Data;

@Data
public class AccountDto {
    private Long id;

    private String nickname;

    private String email;
}
