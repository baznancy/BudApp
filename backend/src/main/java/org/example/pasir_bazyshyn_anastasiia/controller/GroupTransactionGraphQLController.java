package org.example.pasir_bazyshyn_anastasiia.controller;

import org.example.pasir_bazyshyn_anastasiia.dto.GroupTransactionDTO;
import org.example.pasir_bazyshyn_anastasiia.model.User;
import org.example.pasir_bazyshyn_anastasiia.service.GroupTransactionService;
import org.example.pasir_bazyshyn_anastasiia.service.TransactionService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GroupTransactionGraphQLController {

    private final GroupTransactionService groupTransactionService;
    private final TransactionService transactionService;

    public GroupTransactionGraphQLController(GroupTransactionService groupTransactionService, TransactionService transactionService) {
        this.groupTransactionService = groupTransactionService;
        this.transactionService = transactionService;
    }

    @MutationMapping
    public Boolean addGroupTransaction(@Argument GroupTransactionDTO groupTransactionDTO) {
        User user = transactionService.getCurrentUser();
        groupTransactionService.addGroupTransaction(groupTransactionDTO, user);
        return true;
    }
}
