package com.blogapp.post.service.client;

import com.blogapp.post.dto.AccountDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AccountFallback implements AccountFeignClient {
    @Override
    public ResponseEntity<AccountDto> getAccountByNickname(String nickname) {
        log.warn("Invoked fallback method of getAccountByNickname");
        return null;
    }
}
