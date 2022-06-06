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
public class TransactionDeposit {
    private final AccountService accountService;
    private final TransactionDAO transactionDAO;



    @Transactional
    // позволяет пополнять счет аккаунта
    public void execute(String accountFullId, double amount) {
        accountService.deposit(accountFullId, amount);
        Transaction transaction = new Transaction(new Date().toString(), "refill", accountFullId, amount);
        transactionDAO.addTransaction(transaction.getDate(), transaction.getTypeOfOperation(), transaction.getAccountNumber(), transaction.getAmount());
    }


}
