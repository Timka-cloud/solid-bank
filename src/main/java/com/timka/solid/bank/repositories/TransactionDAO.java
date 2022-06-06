package com.timka.solid.bank.repositories;

import com.timka.solid.bank.entities.Transaction;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionDAO extends CrudRepository<Transaction, Long> {
    @Modifying
    @Query(
            value = "INSERT INTO Transaction (date, account_number, amount, type_of_operation) values (:date, :accountNumber, :amount, :type)", nativeQuery = true
    )
    void addTransaction(String date, String type, String accountNumber, double amount);

    List<Transaction> findAllByAccountNumber(String accountFullId);
}
