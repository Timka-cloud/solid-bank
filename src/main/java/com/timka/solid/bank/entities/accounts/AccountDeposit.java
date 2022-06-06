package com.timka.solid.bank.entities.accounts;


public class AccountDeposit extends Account{
    public AccountDeposit(String accountType, Long bankID, String clientID, double balance, boolean withdrawAllowed) {
        super(accountType, bankID, clientID, balance, withdrawAllowed);
    }


    /**
     * можно только снимать.
     * @param accountType
     * @param id
     * @param clientID
     * @param balance
     * @param withdrawAllowed
     */

}
