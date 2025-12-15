package com.blogapp.user.controller;

import com.blogapp.user.dto.UserDto;
import com.blogapp.user.dto.UserEmailDto;
import com.blogapp.user.dto.UpdateNicknameDto;
import com.blogapp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserDto userDto) {
        userService.createUser(userDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update/email")
    public ResponseEntity<Void> updateUser(@RequestBody UserEmailDto userDto) {
        userService.updateUserEmail(userDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/nickname")
    public ResponseEntity<Void> updateUser(@RequestBody UpdateNicknameDto userDto) {
        userService.updateUserNickname(userDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{nickname}")
    public ResponseEntity<UserDto> findPostById(@PathVariable("nickname") String nickname) {
        UserDto user = userService.findUser(nickname);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.ok().build();
    }

}
