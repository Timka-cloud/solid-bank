package com.timka.solid.bank.exceptions;

public class AccountWithdrawException extends RuntimeException{
    public AccountWithdrawException(String message) {
        super(message);
    }
}
