package com.blogapp.authserver.dto;

import com.blogapp.authserver.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoDto {
    private long id;
    private String nickname;
    private String email;
    private Set<Role> roles;
}
