package edisonrmedina.CityBank.repository;

import edisonrmedina.CityBank.entity.bank.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  BankAccountRepository extends MongoRepository<BankAccount,String> {


}
