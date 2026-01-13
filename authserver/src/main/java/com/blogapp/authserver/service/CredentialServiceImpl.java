package com.blogapp.authserver.service;

import com.blogapp.authserver.dto.UserInfoDto;
import com.blogapp.authserver.dto.UserRegisterRequestDto;
import com.blogapp.authserver.entity.Credential;
import com.blogapp.authserver.repository.CredentialRepository;
import com.blogapp.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.blogapp.authserver.entity.Role.USER;

@Service
@RequiredArgsConstructor
public class CredentialServiceImpl implements CredentialService {
    private final PasswordEncoder passwordEncoder;
    private final CredentialRepository credentialRepository;
    private final ModelMapper modelMapper = new ModelMapper();


    @Override
    public void registerUser(UserRegisterRequestDto requestDto) {
        String hashPwd = passwordEncoder.encode(requestDto.getPassword());

        Credential credential = new Credential();
        credential.setNickname(requestDto.getNickname());
        credential.setEmail(requestDto.getEmail());
        credential.setPwd(hashPwd);
        credential.setRoles(Set.of(USER));

        credentialRepository.save(credential);
    }

    @Override
    public UserInfoDto findByNickname(String nickname) {
        Credential credential = credentialRepository.findByNickname(nickname).orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(credential, UserInfoDto.class);
    }
}
