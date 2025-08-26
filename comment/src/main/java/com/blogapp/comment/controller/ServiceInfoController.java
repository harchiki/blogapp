package com.blogapp.comment.controller;

import com.blogapp.comment.dto.ServiceInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/serviceInfo")
@RequiredArgsConstructor
@Slf4j
public class ServiceInfoController {
    private final ServiceInfoDto serviceInfoDto;

    @GetMapping
    public ResponseEntity<ServiceInfoDto> fetchServiceInfo() {
        log.warn("fetchServiceInfo invoked.");
        throw new RuntimeException("Retry attempt");
        //return ResponseEntity.ok(serviceInfoDto);
    }
}
