package com.timka.solid.bank.entities.accounts;

public class CheckingAccount extends AccountWithdraw{


    public CheckingAccount(String accountType, Long bankID, String clientID, double balance, boolean withdrawAllowed) {
        super(accountType, bankID, clientID, balance, withdrawAllowed);
    }
}
