package com.blogapp.authserver.service;

import com.blogapp.authserver.dto.UserInfoDto;
import com.blogapp.authserver.dto.UserRegisterRequestDto;

public interface UserService {
    void registerUser(UserRegisterRequestDto requestDto);
    UserInfoDto findByNickname(String nickname);
}
