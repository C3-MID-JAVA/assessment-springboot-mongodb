package com.sofkau.usrv_accounts_manager.repository;


import com.sofkau.usrv_accounts_manager.model.abstracts.TransactionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TransactionRepository extends MongoRepository<TransactionModel, String> {

}
