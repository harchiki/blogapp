package com.blogapp.user.service;

import com.blogapp.user.dto.UserDto;
import com.blogapp.user.entity.User;
import com.blogapp.user.repository.UserRepository;

import com.blogapp.exception.EntityAlreadyExistsException;
import com.blogapp.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper = new ModelMapper();

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
    public void updateUser(Long id, UserDto userDto) {
        User user = userRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setEmail(userDto.getEmail());
        user.setNickname(userDto.getNickname());

        userRepository.save(user);
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
