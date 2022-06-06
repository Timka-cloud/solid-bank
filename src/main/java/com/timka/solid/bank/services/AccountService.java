package com.timka.solid.bank.services;

import com.timka.solid.bank.dto.AccountDto;
import com.timka.solid.bank.dto.CreationAccountDto;
import com.timka.solid.bank.entities.accounts.AccountWithdraw;

import java.util.List;

public interface AccountService {
    boolean create(CreationAccountDto creationAccountDto);
    void deleteById(String fullAccountId);

    public void deposit(String accountFullId, double amount);
    AccountDto getClientAccount(String accountID, String username);
    AccountWithdraw getClientWithdrawAccount(String clientID, String accountID);
    List<AccountDto> getClientAccounts(String username);
    void withdraw(double amount, String accountFullID);
}
