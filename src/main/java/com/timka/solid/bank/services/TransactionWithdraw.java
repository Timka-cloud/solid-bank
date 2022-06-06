package com.timka.solid.bank.services;

import com.timka.solid.bank.repositories.TransactionDAO;
import com.timka.solid.bank.entities.Transaction;
import com.timka.solid.bank.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TransactionWithdraw {
    private final AccountService accountService;
    private final TransactionDAO transactionDAO;


    @Transactional
    // позволяет снимать деньги со счета(кроме Fixed аккаунтов)
    public void execute(String accountFullId, double amount) {
        accountService.withdraw(amount, accountFullId); // если снимают больше чем есть на счету то не записывать это в транзакцию
        Transaction transaction = new Transaction(new Date().toString(), "withdrawal", accountFullId, amount);
        transactionDAO.addTransaction(transaction.getDate(), transaction.getTypeOfOperation(), transaction.getAccountNumber(), transaction.getAmount());

    }


}
