package com.timka.solid.bank.services;

import com.timka.solid.bank.dto.AccountDto;
import com.timka.solid.bank.dto.CreationAccountDto;
import com.timka.solid.bank.entities.User;
import com.timka.solid.bank.entities.accounts.*;
import com.timka.solid.bank.exceptions.AccountCreationException;
import com.timka.solid.bank.exceptions.AccountNotFound;
import com.timka.solid.bank.exceptions.AccountWithdrawException;
import com.timka.solid.bank.repositories.AccountDAO;
import com.timka.solid.bank.repositories.UserRepository;
import com.timka.solid.bank.response.BodyResponse;
import com.timka.solid.bank.utils.JwtTokenUtil;
import com.timka.solid.bank.utils.ParsedToken;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountDAO accountDAO;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Transactional
    public boolean create(CreationAccountDto creationAccountDto) {
        Account account = null;

        if (creationAccountDto.getAccountType().equals("CHECKING")) {
            account = new Account("CHECKING", creationAccountDto.getBankId(), creationAccountDto.getClientId(), 0.0, true);
        } else if (creationAccountDto.getAccountType().equals("FIXED")) {
            account = new Account("FIXED", creationAccountDto.getBankId(),  creationAccountDto.getClientId(), 0.0, false);
        } else if (creationAccountDto.getAccountType().equals("SAVING")) {
            account = new Account("SAVING", creationAccountDto.getBankId(), creationAccountDto.getClientId(), 0.0, true);
        }

        if (account == null) {
            throw new AccountCreationException("Wrong type of account");
        }
        accountDAO.save(account);
        Long accountID = accountDAO.getLastId();
        accountDAO.setAccountFullId(String.format("%03d%06d", account.getBankID(), accountID), accountID, account.getClientID());
        System.out.println("Bank account created");
        return true;
    }

    @Override
    public void deleteById(String fullAccountId) {
        Account account = accountDAO.findAccountByAccountFullId(fullAccountId);
        if(account == null) {
            throw new AccountNotFound("Account not found by " + fullAccountId);
        }
        accountDAO.delete(account);
    }

    @Override
    @Transactional
    public void deposit(String accountFullId, double amount) {
        Account account = accountDAO.findAccountByAccountFullId(accountFullId);
        if(account == null) {
            throw new AccountNotFound("Account not found by " + accountFullId);
        }
        account.setBalance(account.getBalance() + amount);
        accountDAO.save(account);

    }

    @Override
    public AccountDto getClientAccount(String accountID, String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        Account account = accountDAO.findAccountByAccountFullIdAndClientID(accountID, user.get().getId().toString());
        if(account == null) {
            throw new AccountNotFound("Account not found by " + accountID);
        }
        return new AccountDto(account.getAccountFullId(), account.getAccountType(), account.getClientID(), account.getBalance(), account.isWithdrawAllowed());
    }

    @Override
    public AccountWithdraw getClientWithdrawAccount(String clientID, String accountID) {
        return accountDAO.getClientWithdrawAccount(clientID, accountID);

    }

    @Override
    public List<AccountDto> getClientAccounts(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        List<Account> accountList = accountDAO.findAccountsByClientID(String.valueOf(user.get().getId()));
        return accountList.stream().map(a -> new AccountDto(a.getAccountFullId(), a.getAccountType(), a.getClientID(), a.getBalance(), a.isWithdrawAllowed())).collect(Collectors.toList());

    }

    @Override
    public void withdraw(double amount, String accountFullID) {
        Account account = accountDAO.findAccountByAccountFullId(accountFullID);

        if(account == null) {
            throw new AccountNotFound("Account not found by " + accountFullID);
        }
        if(!account.isWithdrawAllowed()) {
            throw new AccountWithdrawException("You can't withdraw money from fixed account");
        }
        if(amount > account.getBalance()) {
            System.out.println("there are not enough funds in your account");
            throw new AccountWithdrawException("there are not enough funds in your account");
        }
        account.setBalance(account.getBalance() - amount);

        accountDAO.save(account);
        System.out.printf("%.2f$ transferred from %s account\n", amount,accountFullID);
    }
}
