package com.example.bankcards.repository;

import com.example.bankcards.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {

    Optional<TransferEntity> findByRequestId(UUID requestId);
}
