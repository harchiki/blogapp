package com.blogapp.user.service;

import com.blogapp.user.dto.UserDto;

public interface UserService {
    void createUser(UserDto userDto);
    void updateUser(Long id, UserDto userDto);
    UserDto findUser(Long id);
    UserDto findUser(String nickname);
    void delete(Long id);
}
