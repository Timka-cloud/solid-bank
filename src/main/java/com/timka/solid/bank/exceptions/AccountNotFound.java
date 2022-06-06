package com.timka.solid.bank.exceptions;

public class AccountNotFound extends RuntimeException{
    public AccountNotFound(String message) {
        super(message);
    }
}
