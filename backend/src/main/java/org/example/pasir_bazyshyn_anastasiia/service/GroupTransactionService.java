package org.example.pasir_bazyshyn_anastasiia.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.pasir_bazyshyn_anastasiia.dto.GroupTransactionDTO;
import org.example.pasir_bazyshyn_anastasiia.model.*;
import org.example.pasir_bazyshyn_anastasiia.repository.DebtRepository;
import org.example.pasir_bazyshyn_anastasiia.repository.GroupRepository;
import org.example.pasir_bazyshyn_anastasiia.repository.MembershipRepository;
import org.example.pasir_bazyshyn_anastasiia.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class GroupTransactionService {

    private final GroupRepository groupRepository;
    private final MembershipRepository membershipRepository;
    private final DebtRepository debtRepository;
    private final TransactionRepository transactionRepository;

    public GroupTransactionService(GroupRepository groupRepository, MembershipRepository membershipRepository, DebtRepository debtRepository, TransactionRepository transactionRepository) {
        this.groupRepository = groupRepository;
        this.membershipRepository = membershipRepository;
        this.debtRepository = debtRepository;
        this.transactionRepository = transactionRepository;
    }

    public void addGroupTransaction(GroupTransactionDTO dto, User currentUser){
        Group group = groupRepository.findById(dto.getGroupId())
                .orElseThrow(()-> new EntityNotFoundException("Nie znaleziono grupy"));

        List<Membership> members = membershipRepository.findByGroupId(group.getId());
        List<Long> selectedUserIds = dto.getSelectedUserIds();

        if(selectedUserIds == null || selectedUserIds.isEmpty()){
            throw new IllegalArgumentException("Nie wybrano zadnych uzytkownikow");
        }

        double amountPerUser = dto.getAmount() / selectedUserIds.size();

        for( Membership member : members){
            User debtor = member.getUser();
            if (!debtor.getId().equals(currentUser.getId()) && selectedUserIds.contains(debtor.getId())){
                Debt debt = new Debt();
                debt.setDebtor(debtor);
                debt.setCreditor(currentUser);
                debt.setGroup(group);
                debt.setAmount(amountPerUser);
                debt.setTitle(dto.getTitle());
                debtRepository.save(debt);
            }
        }

        Transaction expense = new Transaction();
        expense.setAmount(dto.getAmount()); // Pełna kwota jako wydatek
        expense.setType(TransactionType.EXPENSE);
        expense.setTags("Wydatek grupowy");
        expense.setNotes("Wydatek grupowy w: " + group.getName() + " - " + dto.getTitle());
        expense.setUser(currentUser);
        expense.setTimestamp(LocalDateTime.now());

        transactionRepository.save(expense);
    }

}
