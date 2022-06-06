package com.timka.solid.bank.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


import javax.persistence.*;
import java.util.Date;
@Data
@Entity
@Table(name = "Transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "date")
    private String date;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "amount")
    private double amount;

    @Column(name = "type_of_operation")
    private String typeOfOperation;

    public Transaction(String date, String typeOfOperation, String accountNumber, double amount) {
        this.date = date;
        this.typeOfOperation = typeOfOperation;
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public Transaction() {

    }

    @Override
    public String toString() {
        return String.format("Transaction[id = %d, %s account = %s, amount = %.2f, date = %s",id, typeOfOperation,accountNumber, amount, date);
    }
}
