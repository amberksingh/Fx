package com.example.Fx.service;

import com.example.Fx.model.Account;
import com.example.Fx.model.IdempotencyKey;
import com.example.Fx.repo.AccountRepo;
import com.example.Fx.repo.IdempotencyKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

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

    int counter = 1;

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

    @Transactional
    @Retryable(retryFor = {TransientDataAccessException.class}, maxAttempts = 3, backoff = @Backoff(value = 2000))
    public Account addAccountIdempotent(Account account, String idempotencyKey) {
        log.info("addAccountIdempotent service..");
        log.info("addAccountIdempotent counter {}", counter++);

        //throw new TransientDataAccessResourceException("Simulated transient DB failure");

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
        try {
            IdempotencyKey idempotencyKeyObj = new IdempotencyKey();
            idempotencyKeyObj.setAccountId(save.getId());
            idempotencyKeyObj.setKey(idempotencyKey);
            idempotencyKeyObj.setCreatedAt(LocalDateTime.now());

            keyRepository.save(idempotencyKeyObj);
        } catch (DataIntegrityViolationException e) {
            // Duplicate key → another request won
            IdempotencyKey winner = keyRepository.findById(idempotencyKey).orElseThrow();
            return accountRepo.findById(winner.getAccountId()).orElseThrow();
        }

        return save;
    }

    @Recover
    public Account recover(TransientDataAccessException e, Account account, String idempotencyKey) {

        log.info("recover method for addAccountIdempotent() {}", e);
        throw new TransientDataAccessResourceException("TransientDataAccessResourceException recovery method");
    }
}
