package com.timka.solid.bank.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.ws.rs.core.Response.Status;


@Data
@AllArgsConstructor
public class BodyResponse {
    private String message;
    private Status statusCode;
    private Object body;
}
