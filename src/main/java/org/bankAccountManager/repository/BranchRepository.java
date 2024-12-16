package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {
    Branch findBranchById(int id);

    Boolean existsById(int id);

    Branch findBranchByName(String name);

    List<Branch> findAll();
}
