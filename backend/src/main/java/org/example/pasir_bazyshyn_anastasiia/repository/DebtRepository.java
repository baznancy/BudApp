package org.example.pasir_bazyshyn_anastasiia.repository;


import org.example.pasir_bazyshyn_anastasiia.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long> {
    List<Debt> findByGroupId(Long groupId);

}
