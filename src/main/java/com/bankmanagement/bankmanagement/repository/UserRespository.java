package com.bankmanagement.bankmanagement.repository;

import com.bankmanagement.bankmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRespository extends JpaRepository<User, UUID> {
}
