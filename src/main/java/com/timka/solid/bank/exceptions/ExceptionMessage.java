package com.timka.solid.bank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ExceptionMessage {
    private String message;
    private int statusCode;


}
