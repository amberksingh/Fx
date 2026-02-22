package com.example.Fx.service;

import com.example.Fx.model.Account;
import com.example.Fx.model.IdempotencyKey;
import com.example.Fx.model.Status;
import com.example.Fx.repo.AccountRepo;
import com.example.Fx.repo.IdempotencyKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.dao.TransientDataAccessResourceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
@Slf4j
public class Service {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    IdempotencyKeyRepository keyRepo;

    @Autowired
    IdempotencyService idempotencyService;

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

    //    @Transactional
//    @Retryable(retryFor = {TransientDataAccessException.class}, maxAttempts = 3, backoff = @Backoff(value = 2000))
//    public Account addAccountIdempotent(Account account, String idempotencyKey) {
//        log.info("addAccountIdempotent service..");
//        log.info("addAccountIdempotent counter {}", counter++);
//
//        //throw new TransientDataAccessResourceException("Simulated transient DB failure");
//
//        //check if key exists
//        Optional<IdempotencyKey> byKey = keyRepository.findByKey(idempotencyKey);
//        if (byKey.isPresent()) {
//            log.warn("Idempotency key already exists. Returning old Account ..");
//            Account existingAccount = accountRepo.findById(byKey.get().getAccountId()).orElseThrow();
//            log.info("existing account : {}", existingAccount);
//            return existingAccount;
//        }
//        log.info("Account doesn't exist..adding");
//        Account save = accountRepo.save(account);
//
//        //update idempotency table with account id
//        try {
//            IdempotencyKey idempotencyKeyObj = new IdempotencyKey();
//            idempotencyKeyObj.setAccountId(save.getId());
//            idempotencyKeyObj.setKey(idempotencyKey);
//            idempotencyKeyObj.setCreatedAt(LocalDateTime.now());
//
//            keyRepository.save(idempotencyKeyObj);
//        } catch (DataIntegrityViolationException e) {
//            // Duplicate key → another request won
//            IdempotencyKey winner = keyRepository.findById(idempotencyKey).orElseThrow();
//            return accountRepo.findById(winner.getAccountId()).orElseThrow();
//        }
//
//        return save;
//    }

    @Transactional
    public Account addAccountIdempotent(Account account, String idempotencyKey) throws InterruptedException {

        log.info("addAccountIdempotent service..{}", Thread.currentThread().getName());
        boolean winner = false;

        // 1️⃣ Try to CLAIM the idempotency key
        try {

            idempotencyService.claim(idempotencyKey);
            //keyRepo.insertClaim(idempotencyKey, Status.IN_PROGRESS.name(), LocalDateTime.now());
//            IdempotencyKey key = new IdempotencyKey();
//            key.setKey(idempotencyKey);
//            key.setStatus(Status.IN_PROGRESS);
//            key.setCreatedAt(LocalDateTime.now());

            //IdempotencyKey saveAndFlush = keyRepo.saveAndFlush(key);// important: flush immediately

            IdempotencyKey claimed = keyRepo.findById(idempotencyKey).orElseThrow();
            log.info("saved IdempotencyKey with status IN_PROGRESS : {}, {}", claimed, Thread.currentThread().getName());
            // TEMP for testing IN_PROGRESS
            Thread.sleep(15000);
            //winner = true;

        } catch (DataIntegrityViolationException dup) {
            log.error("Exception occurred while claiming idempotencyKey . Inside catch block {}", dup.getMessage());
            //log.info("Thread name inside catch block {}", Thread.currentThread().getName());
            log.info("Finding existing idempotency key inside catch block {}", Thread.currentThread().getName());
            IdempotencyKey existing = keyRepo.findById(idempotencyKey)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.CONFLICT,
                            "Idempotency key record missing"
                    ));

            if (existing.getStatus() == Status.COMPLETED) {
                log.warn("Found duplicate idempotency key. Returning old Account");
                return accountRepo.findById(existing.getAccountId())
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Completed but account missing"
                        ));
            }

            // IN_PROGRESS
            log.info("Found IN_PROGRESS status name inside catch block {}", Thread.currentThread().getName());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Request already in progress. Retry shortly.");
        }

        // 2️⃣ Only winner creates account
//        if (!winner) {
//            throw new IllegalStateException("Unexpected idempotency state");
//        }

        Account saved = accountRepo.save(account);
        log.info("Account saved : {} , {}", saved, Thread.currentThread().getName());

        // 3️⃣ Mark as COMPLETED
        IdempotencyKey mine = keyRepo.findById(idempotencyKey)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.CONFLICT,
                        "Idempotency key disappeared"
                ));

        mine.setAccountId(saved.getId());
        mine.setStatus(Status.COMPLETED);
        IdempotencyKey saveCompletedIdempotencyKey = keyRepo.save(mine);
        log.info("saved IdempotencyKey COMPLETED : {} {}", saveCompletedIdempotencyKey, Thread.currentThread().getName());

        return saved;
    }

    @Recover
    public Account recover(TransientDataAccessException e, Account account, String idempotencyKey) {

        log.error("recover method for addAccountIdempotent() : ", e);
        throw new TransientDataAccessResourceException("TransientDataAccessResourceException recovery method");
    }
}

//What REQUIRES_NEW did for you
//Before (everything in ONE transaction)
//
//insertClaim() fails with duplicate key
//
//Hibernate/Spring marks the current transaction as rollback-only
//
//even though you catch it and return old Account
//
//at the end Spring tries to commit → 💥 UnexpectedRollbackException
//
//After (insertClaim() in REQUIRES_NEW)
//
//Now there are two transactions:
//
//Txn A (outer) — your main addAccountIdempotent()
//
//It does the “read existing key + read existing account”
//
//It returns normally
//
//✅ It can commit successfully
//
//Txn B (inner) — only insertClaim() (REQUIRES_NEW)
//
//It attempts the insert
//
//duplicate key happens → Txn B rolls back
//
//✅ But Txn A is NOT poisoned
//
//So yes:
//
//✅ The rollback of the REQUIRES_NEW transaction is not “seen” as rollback-only in the main transaction.
//That’s literally why your endpoint now returns the old account fine.
//
//“Is the effect not seen on main txn?”
//
//Two separate meanings here:
//
//1) Does Txn B failure rollback Txn A?
//
//✅ No. They’re independent.
//
//2) Do changes done in Txn B become visible to Txn A?
//
//If Txn B commits, then yes, its committed data is visible.
//
//If Txn B rolls back (your duplicate case), then no changes are made, so nothing new to see.
//
//In your duplicate case, Txn B didn’t commit anything — it failed — so of course Txn A doesn’t “see” any new insert.
//
//Why your output looks correct now
//
//Your logs show:
//
//insert claim failed (Txn B rolled back)
//
//you caught exception, loaded existing key + account (Txn A)
//
//controller logs account returned ✅
//
//That means your idempotency replay path is working.
//
//One important detail (so you don’t get surprised later)
//
//If you keep REQUIRES_NEW on the repository method directly, it works, but many teams prefer this cleaner design:
//
//Outer transaction in service
//
//A separate IdempotencyClaimService with REQUIRES_NEW
//
//Because transactional proxies behave more predictably at service layer
//
//But functionally, what you did is fine.



//git checkout -b feature-idempotency (-b → create new branch)
//OR
//git branch feature-idempotency
//git checkout feature-idempotency
