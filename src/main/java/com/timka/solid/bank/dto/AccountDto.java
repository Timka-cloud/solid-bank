package com.timka.solid.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDto {
    private String accountFullId;
    private String accountType;
    private String clientID;
    private double balance;
    private boolean withdrawAllowed;

}
