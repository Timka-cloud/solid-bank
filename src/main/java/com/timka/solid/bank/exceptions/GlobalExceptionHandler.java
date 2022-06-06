package com.timka.solid.bank.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@ControllerAdvice
public class GlobalExceptionHandler
{
    @ResponseBody
    @ExceptionHandler(AccountNotFound.class)
    public ResponseEntity<ExceptionMessage> accountNotFound(AccountNotFound ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(AccountCreationException.class)
    public ResponseEntity<ExceptionMessage> creationAccount(AccountCreationException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionMessage> updatingAccount(IllegalArgumentException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(AccountWithdrawException.class)
    public ExceptionMessage withdrawMoney(AccountWithdrawException ex) {
        return new ExceptionMessage(ex.getMessage(), HttpStatus.OK.value());
    }

    @ResponseBody
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionMessage> withdrawMoney(UserNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionMessage(ex.getMessage(), HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
}
