package com.blogapp.account.service;

import com.blogapp.account.dto.AccountDto;

public interface AccountService {
    void createAccount(AccountDto accountDto);
    void updateAccount(Long id, AccountDto accountDto);
    AccountDto findAccount(Long id);
    void delete(Long id);
}
