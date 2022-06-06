package com.timka.solid.bank.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreationAccountDto {
    private String accountType;
    private String clientId = "1";
    private Long bankId = 1L;

}
