package com.example.bankcards.repository;

import com.example.bankcards.entity.CardEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Long>, JpaSpecificationExecutor<CardEntity> {

    Optional<CardEntity> findByIdAndOwner_LinkOwnerId(Long id, Long linkOwnerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<CardEntity> findByOwner_LinkOwnerIdAndNumberCard_Number(Long linkOwnerId, String numberCard);
}
