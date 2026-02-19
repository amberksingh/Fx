package com.example.Fx.service;

import com.example.Fx.model.Account;
import com.example.Fx.repo.Repo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Slf4j
public class Service {

    @Autowired
    Repo repo;

    public Account addAccount(Account account) {
        log.info("addAccount service..");
        return repo.save(account);
    }

    public List<Account> addMultipleAccounts(List<Account> accounts) {
        log.info("addMultipleAccounts service..");
        return repo.saveAll(accounts);
    }

    public Account getAccountById(String accId) {
        log.info("getAccountById service..");
        Optional<Account> byId = repo.findById(Long.valueOf(accId));
        return byId.orElse(null);
    }

    public List<Account> getAllAccounts() {
        log.info("getAllAccounts service..");
        return repo.findAll();
    }

    public Page<Account> getAllAccountsV2(Pageable pageable) {
        log.info("getAllAccountsV2 service..");
        return repo.findAll(pageable);
    }
}
