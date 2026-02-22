package com.example.Fx.service;

import com.example.Fx.model.Status;
import com.example.Fx.repo.IdempotencyKeyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class IdempotencyService {

    @Autowired
    IdempotencyKeyRepository keyRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void claim(String key) {
        log.info("claim() service..{}", Thread.currentThread().getName());
        keyRepo.insertClaim(key, Status.IN_PROGRESS.name(), LocalDateTime.now());
    }
}
