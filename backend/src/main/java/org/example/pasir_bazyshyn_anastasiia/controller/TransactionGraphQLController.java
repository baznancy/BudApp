package org.example.pasir_bazyshyn_anastasiia.controller;


import jakarta.validation.Valid;
import org.example.pasir_bazyshyn_anastasiia.dto.BalanceDto;
import org.example.pasir_bazyshyn_anastasiia.dto.TransactionDTO;
import org.example.pasir_bazyshyn_anastasiia.model.Transaction;
import org.example.pasir_bazyshyn_anastasiia.model.User;
import org.example.pasir_bazyshyn_anastasiia.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class TransactionGraphQLController {

    private final TransactionService transactionService;

    public TransactionGraphQLController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @QueryMapping
    public List<Transaction> transactions() {
        return transactionService.getAllTransactions();
    }

    @MutationMapping
    public Transaction addTransaction(@Valid @Argument TransactionDTO transactionDTO) {
        return transactionService.createTransaction(transactionDTO);
    }

    @MutationMapping
    public Transaction updateTransaction(@Argument Long id,@Valid @Argument TransactionDTO transactionDTO) {
        return transactionService.updateTransaction(id, transactionDTO);
    }

    @MutationMapping
    public void deleteTransaction(@Argument Long id) {
       transactionService.deleteTransaction(id);
    }
    @QueryMapping
    public BalanceDto userBalance() {
        User user  =  transactionService.getCurrentUser();
        return transactionService.getUserBalance(user);
    }
    @QueryMapping
    public List<Transaction> filteredTransactions(
            @Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @Argument String type
    ) {
        return transactionService.getUserTransactionsFiltered(startDate, endDate, type);
    }

    @QueryMapping
    public BalanceDto filteredBalance(
            @Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Argument @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return transactionService.getUserBalanceFiltered(startDate, endDate);
    }
}
