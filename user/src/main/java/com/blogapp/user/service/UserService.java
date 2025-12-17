package com.blogapp.user.service;

import com.blogapp.user.dto.UpdateNicknameDto;
import com.blogapp.user.dto.UserDto;
import com.blogapp.user.dto.UserEmailDto;

public interface UserService {
    void createUser(UserDto userDto);
    void updateUserEmail(UserEmailDto userDto);
    void updateUserNickname(UpdateNicknameDto userDto);
    UserDto findUser(Long id);
    UserDto findUser(String nickname);
    void delete(Long id);
    void rollbackUserNickname(UpdateNicknameDto updateNickname);
}
