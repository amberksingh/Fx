package com.example.Fx.service;

import com.example.Fx.model.Account;
import com.example.Fx.model.IdempotencyKey;
import com.example.Fx.repo.AccountRepo;
import com.example.Fx.repo.IdempotencyKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Slf4j
public class Service {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    IdempotencyKeyRepository keyRepository;

    public Account addAccount(Account account) {
        log.info("addAccount service..");
        return accountRepo.save(account);
    }

    public List<Account> addMultipleAccounts(List<Account> accounts) {
        log.info("addMultipleAccounts service..");
        return accountRepo.saveAll(accounts);
    }

    public Account getAccountById(String accId) {
        log.info("getAccountById service..");
        Optional<Account> byId = accountRepo.findById(Long.valueOf(accId));
        return byId.orElse(null);
    }

    public List<Account> getAllAccounts() {
        log.info("getAllAccounts service..");
        return accountRepo.findAll();
    }

    public Page<Account> getAllAccountsV2(Pageable pageable) {
        log.info("getAllAccountsV2 service..");
        return accountRepo.findAll(pageable);
    }

    public Account updateAccountById(String accId, Account account) {
        Optional<Account> byId = accountRepo.findById(Long.valueOf(accId));
        if (byId.isPresent()) {
            log.info("old Account object : {}", byId.get());
        }
        log.info("updateAccountById service..");
        account.setId(Long.valueOf(accId));
        Account save = accountRepo.save(account);
        log.info("Updated account : {}", save);
        return save;
    }

    public Account addAccountIdempotent(Account account, String idempotencyKey) {
        log.info("addAccountIdempotent service..");
        //check if key exists
        Optional<IdempotencyKey> byKey = keyRepository.findByKey(idempotencyKey);
        if (byKey.isPresent()) {
            log.warn("Idempotency key already exists. Returning old Account ..");
            Account existingAccount = accountRepo.findById(byKey.get().getAccountId()).orElseThrow();
            log.info("existing account : {}", existingAccount);
            return existingAccount;
        }
        log.info("Account doesn't exist..adding");
        Account save = accountRepo.save(account);

        //update idempotency table with account id
        IdempotencyKey idempotencyKeyObj = new IdempotencyKey();
        idempotencyKeyObj.setAccountId(save.getId());
        idempotencyKeyObj.setKey(idempotencyKey);
        idempotencyKeyObj.setCreatedAt(LocalDateTime.now());

        keyRepository.save(idempotencyKeyObj);

        return save;
    }
}
