package com.blogapp.account.controller;

import com.blogapp.account.dto.AccountDto;
import com.blogapp.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<Void> createAccount(@RequestBody AccountDto accountDto) {
        accountService.createAccount(accountDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        accountService.updateAccount(id, accountDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id) {
        AccountDto account = accountService.findAccount(id);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> createAccount(@PathVariable Long id) {
        accountService.delete(id);
        return ResponseEntity.ok().build();
    }

}
