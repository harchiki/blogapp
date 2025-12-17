package com.blogapp.user.service;

import com.blogapp.user.dto.UserDto;
import com.blogapp.user.dto.UserEmailDto;
import com.blogapp.user.dto.UpdateNicknameDto;
import com.blogapp.user.entity.User;
import com.blogapp.user.repository.UserRepository;

import com.blogapp.exception.EntityAlreadyExistsException;
import com.blogapp.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final StreamBridge bridge;

    @Override
    public void createUser(UserDto userDto) {
        Optional<User> optUser = userRepository.findById(userDto.getId());

        if (optUser.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        User newUser = new User();
        modelMapper.map(userDto, newUser);

        userRepository.save(newUser);
    }

    @Override
    public void updateUserEmail(UserEmailDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(EntityNotFoundException::new);
        user.setEmail(userDto.getEmail());

        userRepository.save(user);
    }

    @Override
    public void updateUserNickname(UpdateNicknameDto nicknameDto) {
        User user = userRepository.findByNickname(nicknameDto.getOldNickname()).orElseThrow(EntityNotFoundException::new);
        user.setNickname(nicknameDto.getNewNickname());

        userRepository.save(user);
        updatePostNickname(nicknameDto);
    }

    @Override
    public void rollbackUserNickname(UpdateNicknameDto updateNickname) {
        // retrieve the one just updated with newNickname
        User user = userRepository.findByNickname(updateNickname.getNewNickname()).orElseThrow(EntityNotFoundException::new);
        user.setNickname(updateNickname.getOldNickname());

        userRepository.save(user);
    }

    private void updatePostNickname(UpdateNicknameDto nicknameDto) {
        log.info("Sending updatePostNickname request for the details: {}", nicknameDto);
        var result = bridge.send("updatePostNickname-out-0",nicknameDto);
        log.info("Is the updatePostNickname request successfully triggered ? : {}", result);
    }

    @Override
    public UserDto findUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        UserDto userDto = new UserDto();

        modelMapper.map(user, userDto);
        return userDto;
    }

    @Override
    public UserDto findUser(String nickname) {
        User user = userRepository.findByNickname(nickname).orElseThrow(EntityNotFoundException::new);
        UserDto userDto = new UserDto();

        modelMapper.map(user, userDto);
        return userDto;
    }

    @Override
    public void delete(Long id) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }
}
