package com.timka.solid.bank.response;

import com.timka.solid.bank.dto.AccountDto;
import com.timka.solid.bank.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ResponseTransactions {
    private String message;
    private int statusCode;
    private List<TransactionDto> transactionDtoList;
}
