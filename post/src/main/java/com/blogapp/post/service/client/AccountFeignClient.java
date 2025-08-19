package com.blogapp.post.service.client;

import com.blogapp.post.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ACCOUNT")
public interface AccountFeignClient {
    @GetMapping(value = "/account/{id}", consumes = "application/json")
    ResponseEntity<AccountDto> getAccount(@PathVariable("id") Long id);
}
