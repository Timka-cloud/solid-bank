package com.timka.solid.bank.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseMessage {
    private String message;
    private int statusCode;
}
