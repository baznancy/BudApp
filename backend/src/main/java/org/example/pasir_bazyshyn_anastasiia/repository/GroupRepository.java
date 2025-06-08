package org.example.pasir_bazyshyn_anastasiia.repository;


import org.example.pasir_bazyshyn_anastasiia.model.Group;
import org.example.pasir_bazyshyn_anastasiia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByMemberships_User(User user);

}
