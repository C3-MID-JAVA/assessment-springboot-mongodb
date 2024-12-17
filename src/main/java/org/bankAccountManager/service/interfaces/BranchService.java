package org.bankAccountManager.service.interfaces;


import org.bankAccountManager.entity.Branch;

import java.util.List;

public interface BranchService {
    Branch createBranch(Branch branch);

    Branch getBranchById(int id);

    Branch getBranchByName(String name);

    List<Branch> getAllBranches();

    Branch updateBranch(Branch branch);

    void deleteBranch(Branch branch);
}
