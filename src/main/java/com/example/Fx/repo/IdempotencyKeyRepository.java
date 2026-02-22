package com.example.Fx.repo;

import com.example.Fx.model.IdempotencyKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface IdempotencyKeyRepository extends JpaRepository<IdempotencyKey, String> {

    //Optional<IdempotencyKey> findByKey(String key);

    @Modifying
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    @Query(nativeQuery = true, value = "INSERT INTO idempotency_keys (idempotency_key, status, created_at) VALUES (:key, :status, :createdAt)")
    void insertClaim(@Param("key") String key, @Param("status") String status, @Param("createdAt") LocalDateTime createdAt);
}
