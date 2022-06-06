package com.timka.solid.bank.response;

import com.timka.solid.bank.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class ResponseSingleAcc {
    private String message;
    private int statusCode;
    private AccountDto accountDto;
}
