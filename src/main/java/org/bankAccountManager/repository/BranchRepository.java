package org.bankAccountManager.repository;

import org.bankAccountManager.entity.Branch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends MongoRepository<Branch, Long> {
    Branch findBranchById(int id);

    Boolean existsById(int id);

    Branch findBranchByName(String name);

    List<Branch> findAll();
}
