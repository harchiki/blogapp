package com.blogapp.account.service;

import com.blogapp.account.dto.AccountDto;

public interface AccountService {
    void createAccount(AccountDto accountDto);
    void updateAccount(Long id, AccountDto accountDto);
    AccountDto findAccount(Long id);
    AccountDto findAccount(String nickname);
    void delete(Long id);
}
