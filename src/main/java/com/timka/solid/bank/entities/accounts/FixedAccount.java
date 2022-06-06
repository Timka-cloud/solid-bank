package com.timka.solid.bank.entities.accounts;

public class FixedAccount extends AccountDeposit{


    public FixedAccount(String accountType, Long bankID, String clientID, double balance, boolean withdrawAllowed) {
        super(accountType, bankID, clientID, balance, withdrawAllowed);
    }
}
