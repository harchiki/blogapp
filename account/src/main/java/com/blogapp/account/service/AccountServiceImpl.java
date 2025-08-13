package com.blogapp.account.service;


import com.blogapp.account.dto.AccountDto;
import com.blogapp.account.entity.Account;
import com.blogapp.account.exception.EntityAlreadyExistsException;
import com.blogapp.account.exception.ResourceNotFoundException;
import com.blogapp.account.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public void createAccount(AccountDto accountDto) {
        Optional<Account> optAccount = accountRepository.findById(accountDto.getId());

        if (optAccount.isPresent()) {
            throw new EntityAlreadyExistsException();
        }

        Account newAccount = new Account();
        modelMapper.map(accountDto, newAccount);

        accountRepository.save(newAccount);
    }

    @Override
    public void updateAccount(Long id, AccountDto accountDto) {
        Account account = accountRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        account.setEmail(accountDto.getEmail());
        account.setNickname(accountDto.getNickname());

        accountRepository.save(account);
    }

    @Override
    public AccountDto findAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        AccountDto accountDto = new AccountDto();

        modelMapper.map(account, accountDto);
        return accountDto;
    }

    @Override
    public void delete(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        accountRepository.delete(account);
    }
}
