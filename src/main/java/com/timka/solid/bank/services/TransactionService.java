package com.timka.solid.bank.services;

import com.timka.solid.bank.dto.AccountDto;
import com.timka.solid.bank.dto.TransactionDto;
import com.timka.solid.bank.entities.Transaction;
import com.timka.solid.bank.exceptions.AccountNotFound;
import com.timka.solid.bank.repositories.TransactionDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionDAO transactionDAO;




    public List<TransactionDto> findByAccountFullId(String accountFullId) {

        List<Transaction> list = transactionDAO.findAllByAccountNumber(accountFullId);
        return list.stream().map((t) -> new TransactionDto(t.getDate(), t.getAccountNumber(), t.getAmount(), t.getTypeOfOperation())).collect(Collectors.toList());
    }
}
