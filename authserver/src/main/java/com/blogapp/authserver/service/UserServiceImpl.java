package com.blogapp.authserver.service;

import com.blogapp.authserver.dto.UserInfoDto;
import com.blogapp.authserver.dto.UserRegisterRequestDto;
import com.blogapp.authserver.entity.UserInfo;
import com.blogapp.authserver.repository.UserRepository;
import com.blogapp.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.blogapp.authserver.entity.Role.USER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public void registerUser(UserRegisterRequestDto requestDto) {
        String hashPwd = passwordEncoder.encode(requestDto.getPassword());

        UserInfo userInfo = new UserInfo();
        userInfo.setNickname(requestDto.getNickname());
        userInfo.setEmail(requestDto.getEmail());
        userInfo.setPwd(hashPwd);
        userInfo.setRoles(Set.of(USER));

        userRepository.save(userInfo);
    }

    @Override
    public UserInfoDto findByNickname(String nickname) {
        UserInfo userInfo = userRepository.findByNickname(nickname).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(userInfo, UserInfoDto.class);
    }
}
