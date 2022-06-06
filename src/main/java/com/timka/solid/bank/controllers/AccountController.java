package com.timka.solid.bank.controllers;

import com.timka.solid.bank.services.TransactionDeposit;
import com.timka.solid.bank.services.TransactionWithdraw;
import com.timka.solid.bank.dto.AccountDto;
import com.timka.solid.bank.dto.CreationAccountDto;
import com.timka.solid.bank.dto.TransactionDto;
import com.timka.solid.bank.response.ResponseManyAcc;
import com.timka.solid.bank.response.ResponseMessage;
import com.timka.solid.bank.response.ResponseSingleAcc;
import com.timka.solid.bank.response.ResponseTransactions;
import com.timka.solid.bank.services.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
    private final TransactionDeposit transactionDeposit;
    private final TransactionWithdraw transactionWithdraw;

    private final TransactionService transactionService;



    @GetMapping
    public ResponseEntity<ResponseManyAcc> getClientAccounts(HttpServletRequest request) {

        String jwtToken = request.getHeader("Authorization").replace("Bearer ", "");
        System.out.println(jwtToken);
        List<AccountDto> clientAccounts = accountService.getClientAccounts(jwtToken);
        return new ResponseEntity<>(ResponseManyAcc.builder().statusCode(HttpStatus.OK.value()).message(clientAccounts.size() + " accounts returned").accountList(clientAccounts).build(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage> create(@RequestBody CreationAccountDto creationAccountDto) {
        accountService.create(creationAccountDto);
        return new ResponseEntity<>(ResponseMessage.builder().message("Account has been created").statusCode(HttpStatus.OK.value()).build(), HttpStatus.OK);
    }

    @GetMapping("/{account_id}")
    public ResponseEntity<ResponseSingleAcc> getClientAccount(@PathVariable(name = "account_id") String fullAccountId) {
        AccountDto clientAccount = accountService.getClientAccount(fullAccountId);
        return new ResponseEntity<>(ResponseSingleAcc.builder().statusCode(HttpStatus.OK.value()).message("1 account returned").accountDto(clientAccount).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{account_id}")
    public ResponseEntity<ResponseMessage> delete(@PathVariable(name = "account_id") String fullAccountId) {
        accountService.deleteById(fullAccountId);
        return new ResponseEntity<>(ResponseMessage.builder().statusCode(HttpStatus.OK.value()).message("Account 1 deleted").build(), HttpStatus.OK);
    }

    @PostMapping("/{account_id}/deposit")
    public ResponseEntity<ResponseMessage> depositBalance(@PathVariable(name = "account_id") String fullAccountId, @RequestParam double balance) {
        if(balance <= 0) {
            throw new IllegalArgumentException("Amount should be more than 0");
        }
        transactionDeposit.execute(fullAccountId, balance);
        return new ResponseEntity<>(ResponseMessage.builder().message(String.format("%.2f$ transferred to %s account", balance, fullAccountId)).statusCode(HttpStatus.OK.value()).build(), HttpStatus.OK);
    }

    @PostMapping("/{account_id}/withdraw")
    public ResponseEntity<ResponseMessage> withdrawBalance(@PathVariable(name = "account_id") String fullAccountId, @RequestParam double balance) {
        if(balance <= 0) {
            throw new IllegalArgumentException("Amount should be more than 0");
        }
        transactionWithdraw.execute(fullAccountId, balance);
        return new ResponseEntity<>(ResponseMessage.builder().message(String.format("%.2f$ transferred from %s account", balance, fullAccountId)).statusCode(HttpStatus.OK.value()).build(), HttpStatus.OK);
    }

    @GetMapping("/{account_id}/transactions")
    public ResponseEntity<ResponseTransactions> getTransactionsByAccountId(@PathVariable(name = "account_id") String fullAccountId) {
        List<TransactionDto> list = transactionService.findByAccountFullId(fullAccountId);
        return new ResponseEntity<>(ResponseTransactions.builder().statusCode(HttpStatus.OK.value()).message(list.size() + " transactions returned").transactionDtoList(list).build(), HttpStatus.OK);
    }
}
