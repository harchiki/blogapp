package com.blogapp.post.service.client;

import com.blogapp.post.dto.AccountDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "ACCOUNT", fallback = AccountFallback.class)
public interface AccountFeignClient {
    @GetMapping(value = "/account/{nickname}", consumes = "application/json")
    ResponseEntity<AccountDto> getAccountByNickname(@PathVariable("nickname") String nickname);
}
