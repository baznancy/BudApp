package org.example.pasir_bazyshyn_anastasiia.repository;


import org.example.pasir_bazyshyn_anastasiia.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {
    List<Membership> findByGroupId(Long groupId);

}
