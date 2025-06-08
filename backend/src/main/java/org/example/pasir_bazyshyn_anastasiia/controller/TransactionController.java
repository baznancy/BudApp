package org.example.pasir_bazyshyn_anastasiia.controller;

import jakarta.validation.Valid;
import org.example.pasir_bazyshyn_anastasiia.dto.TransactionDTO;
import org.example.pasir_bazyshyn_anastasiia.model.Transaction;
import org.example.pasir_bazyshyn_anastasiia.repository.TransactionRepository;
import org.example.pasir_bazyshyn_anastasiia.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

    @RestController
    @RequestMapping("/api/transactions")
    public class TransactionController {

        @Autowired
        private TransactionRepository transactionRepository;
        private final TransactionService transactionService;

        public TransactionController(TransactionService transactionService) {
            this.transactionService = transactionService;
        }

        @GetMapping
        public ResponseEntity<List<Transaction>> getAllTransactions() {
            return ResponseEntity.ok(transactionService.getAllTransactions());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
            return ResponseEntity.ok(transactionService.getTransactionById(id));
        }

        @PutMapping("/{id}")
        public ResponseEntity<Transaction> updateTransaction(@PathVariable Long id, @Valid @RequestBody TransactionDTO transactionDTO) {
            Transaction updatedTransaction = transactionService.updateTransaction(id, transactionDTO);
            return ResponseEntity.ok(updatedTransaction);
        }

        @PostMapping
        public ResponseEntity<Transaction> createTransaction( @Valid @RequestBody TransactionDTO transactionDTO) {
            Transaction createdTransaction = transactionService.createTransaction(transactionDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
            transactionService.deleteTransaction(id);
            return ResponseEntity.noContent().build();
        }

    }

