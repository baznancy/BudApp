package org.example.pasir_bazyshyn_anastasiia.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.pasir_bazyshyn_anastasiia.dto.BalanceDto;
import org.example.pasir_bazyshyn_anastasiia.dto.TransactionDTO;
import org.example.pasir_bazyshyn_anastasiia.model.Transaction;
import org.example.pasir_bazyshyn_anastasiia.model.TransactionType;
import org.example.pasir_bazyshyn_anastasiia.model.User;
import org.example.pasir_bazyshyn_anastasiia.repository.TransactionRepository;
import org.example.pasir_bazyshyn_anastasiia.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, UserRepository userRepository1) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository1;
    }

    public List<Transaction> getAllTransactions() {
        User user = getCurrentUser();
        return transactionRepository.findAllByUser(user);
    }

    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono transakcji o ID" + id));
    }

    public Transaction updateTransaction(Long id, TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Nie znaleziono transakcji o ID" + id));

        if(!transaction.getUser().getEmail().equals(getCurrentUser().getEmail())) {
            throw new SecurityException("Brak dostępu do edycji tej transakcji");
        }

        transaction.setAmount(transactionDTO.getAmount());
        transaction.setNotes(transactionDTO.getNotes());
        transaction.setTags(transactionDTO.getTags());
        transaction.setType(TransactionType.valueOf(transactionDTO.getType()));

        return transactionRepository.save(transaction);
    }

    public Transaction createTransaction(TransactionDTO transactionDTO) {
        Transaction transaction = new Transaction();
        transaction.setAmount(transactionDTO.getAmount());
        transaction.setType(TransactionType.valueOf(transactionDTO.getType()));
        transaction.setTags(transactionDTO.getTags());
        transaction.setNotes(transactionDTO.getNotes());
        transaction.setUser(getCurrentUser());
        transaction.setTimestamp(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    public void deleteTransaction(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono transakcji o ID " + id));

        transactionRepository.delete(transaction);
    }

    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("nie znaleziono zalogowanego uzytkownilka"));
    }

    public BalanceDto getUserBalance(User user) {
        List<Transaction> userTransactions = transactionRepository.findAllByUser(user);

        double income = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return  new BalanceDto(income, expense, income - expense);
    }

    /**
     * Pobiera transakcje użytkownika z możliwością filtrowania po czasie i typie
     */
    public List<Transaction> getUserTransactionsFiltered(
            LocalDateTime startDate,
            LocalDateTime endDate,
            String type
    ) {
        User currentUser = getCurrentUser();

        if (startDate == null && endDate == null) {
            // Brak filtrów czasowych
            if (type != null && !type.isEmpty()) {
                return transactionRepository.findAllByUserAndType(
                        currentUser,
                        TransactionType.valueOf(type)
                );
            } else {
                return transactionRepository.findAllByUser(currentUser);
            }
        } else {
            // Filtrowanie po czasie
            LocalDateTime start = startDate != null ? startDate : LocalDateTime.of(1970, 1, 1, 0, 0);
            LocalDateTime end = endDate != null ? endDate : LocalDateTime.now();

            if (type != null && !type.isEmpty()) {
                return transactionRepository.findAllByUserAndTypeAndTimestampBetween(
                        currentUser,
                        TransactionType.valueOf(type),
                        start,
                        end
                );
            } else {
                return transactionRepository.findAllByUserAndTimestampBetween(
                        currentUser,
                        start,
                        end
                );
            }
        }
    }

    /**
     * Oblicza bilans użytkownika z możliwością filtrowania po czasie
     */
    public BalanceDto getUserBalanceFiltered(LocalDateTime startDate, LocalDateTime endDate) {
        User currentUser = getCurrentUser();
        List<Transaction> userTransactions;

        if (startDate == null && endDate == null) {
            userTransactions = transactionRepository.findAllByUser(currentUser);
        } else {
            LocalDateTime start = startDate != null ? startDate : LocalDateTime.of(1970, 1, 1, 0, 0);
            LocalDateTime end = endDate != null ? endDate : LocalDateTime.now();
            userTransactions = transactionRepository.findAllByUserAndTimestampBetween(currentUser, start, end);
        }

        double income = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.INCOME)
                .mapToDouble(Transaction::getAmount)
                .sum();

        double expense = userTransactions.stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .mapToDouble(Transaction::getAmount)
                .sum();

        return new BalanceDto(income, expense, income - expense);
    }


}
