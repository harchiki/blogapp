package com.blogapp.authserver.service;

import com.blogapp.authserver.entity.Role;
import com.blogapp.authserver.entity.UserInfo;
import com.blogapp.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String nickname) throws UsernameNotFoundException {
        UserInfo user = userRepository.findByNickname(nickname).orElseThrow(() -> new
                UsernameNotFoundException("User details not found for the user: " + nickname));
        List<GrantedAuthority> authorities = user.getRoles().stream().map(authority -> new
                SimpleGrantedAuthority(Role.USER.name())).collect(Collectors.toList());
        return new User(user.getNickname(), user.getPwd(), authorities);
    }
}
