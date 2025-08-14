package com.blogapp.account.controller;

import com.blogapp.account.dto.ServiceInfoDto;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@RequiredArgsConstructor
public class ServiceInfoController {
    private final ServiceInfoDto serviceInfoDto;

    @GetMapping
    public ResponseEntity<ServiceInfoDto> findPostById() {
        return ResponseEntity.ok(serviceInfoDto);
    }
}
