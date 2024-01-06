package com.either.repository;

import com.either.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface AccountRepository extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {
}
