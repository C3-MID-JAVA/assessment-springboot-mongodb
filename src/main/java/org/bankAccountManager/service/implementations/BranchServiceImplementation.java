package org.bankAccountManager.service.implementations;

import org.bankAccountManager.entity.Branch;
import org.bankAccountManager.repository.BranchRepository;
import org.bankAccountManager.service.interfaces.BranchService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BranchServiceImplementation implements BranchService {

    private final BranchRepository branchRepository;

    public BranchServiceImplementation(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public Branch createBranch(Branch branch) {
        if (branchRepository.existsById(branch.getId()))
            throw new IllegalArgumentException("Branch already exists");
        return branchRepository.save(branch);
    }

    @Override
    public Branch getBranchById(int id) {
        return branchRepository.findBranchById(id);
    }

    @Override
    public Branch getBranchByName(String name) {
        return branchRepository.findBranchByName(name);
    }

    @Override
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @Override
    public Branch updateBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    @Override
    public void deleteBranch(Branch branch) {
        branchRepository.delete(branch);
    }
}
