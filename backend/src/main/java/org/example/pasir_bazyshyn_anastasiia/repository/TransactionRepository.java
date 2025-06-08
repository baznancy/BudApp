package org.example.pasir_bazyshyn_anastasiia.repository;

import org.example.pasir_bazyshyn_anastasiia.model.Transaction;
import org.example.pasir_bazyshyn_anastasiia.model.TransactionType;
import org.example.pasir_bazyshyn_anastasiia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByUser(User user);

    List<Transaction> findAllByUserAndTimestampBetween(
            User user,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    // Metoda do filtrowania po czasie i typie transakcji
    List<Transaction> findAllByUserAndTypeAndTimestampBetween(
            User user,
            TransactionType type,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

    // Metoda do filtrowania tylko po typie
    List<Transaction> findAllByUserAndType(User user, TransactionType type);
}