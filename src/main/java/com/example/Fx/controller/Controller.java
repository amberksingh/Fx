package com.example.Fx.controller;

import com.example.Fx.model.Account;
import com.example.Fx.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fx")
@Slf4j
public class Controller {

    @Autowired
    Service service;

    @GetMapping("/demo")
    public String demo() {
        log.info("fx demo msg..");
        return "fx demo msg..";
    }

    //✅ 2️⃣ ResponseEntity<?>
    //
    //? means:
    //
    //“This endpoint may return any type.”
    //
    //Example:
    //
    //@PostMapping
    //public ResponseEntity<?> create(@RequestBody Account account) {
    //    if (account.getEmail() == null) {
    //        return ResponseEntity.badRequest()
    //                .body("Email is required");
    //    }
    //
    //    Account saved = service.save(account);
    //    return ResponseEntity.ok(saved);
    //}
    //
    //✔ What it means:
    //
    //Flexible
    //
    //Can return Account, String, Map, ErrorResponse, etc.
    //
    //Less strict
    //
    //Not type-safe
    //🔹 Wildcard it is called
    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccount(@RequestBody Account account) {

        log.info("addAccount controller..");
        Account savedAccount = service.addAccount(account);
        log.info("account added : {}", savedAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PostMapping("/addAccountIdempotent")
    public ResponseEntity<?> addAccountIdempotent(
            @RequestBody Account account,
            @RequestHeader("Idempotency-Key") String idempotencyKey
    ) throws InterruptedException {

        log.info("addAccountIdempotent controller..");
        Account savedAccount = service.addAccountIdempotent(account, idempotencyKey);
        log.info("addAccountIdempotent added : {}", savedAccount);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccount);
    }

    @PostMapping("/addMultipleAccounts")
    public ResponseEntity<List<Account>> addMultipleAccounts(@RequestBody List<Account> accounts) {

        log.info("addMultipleAccounts controller..");
        List<Account> savedAccounts = service.addMultipleAccounts(accounts);
        log.info("accounts added : {}", savedAccounts);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAccounts);
    }

    @GetMapping("/getAccountById/{id}")
    public ResponseEntity<Account> getAccountById(@PathVariable("id") String accId) {
        log.info("getAccountById controller..");
        Account account = service.getAccountById(accId);
        log.info("account fetched : {}", account);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @GetMapping("/getAllAccounts")
    public ResponseEntity<List<Account>> getAllAccounts() {
        log.info("getAllAccounts controller..");
        List<Account> accounts = service.getAllAccounts();
        log.info("accounts fetched : {}", accounts);
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @GetMapping("/v2/getAllAccounts")
    public ResponseEntity<Page<Account>> getAllAccountsV2(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "createdAt,desc") String sort
    ) {
        log.info("getAllAccountsV2 controller..");
        String[] parts = sort.split(",");
        String fieldName = parts[0];
        Sort.Direction dir = (parts.length > 1 && parts[1].equalsIgnoreCase("asc"))
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(dir, fieldName));
        return new ResponseEntity<>(service.getAllAccountsV2(pageable), HttpStatus.OK);
    }

    @PutMapping("/updateAccountById/{id}")
    public ResponseEntity<Account> updateAccountById(@PathVariable("id") String accId, @RequestBody Account account) {
        log.info("updateAccountById controller..");
        return ResponseEntity.status(HttpStatus.CREATED).body(service.updateAccountById(accId, account));
    }

    //git init
    //git add README.md
    //git commit -m "first commit"
    //git branch -M main
    //git remote add origin https://github.com/amberksingh/SpringGit.git
    //git push -u origin main

    //git branch -vv will show:
    //
    //* feature-idempotency 43d8d33 [origin/feature-idempotency]
}
