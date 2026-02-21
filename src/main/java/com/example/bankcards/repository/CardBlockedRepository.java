package com.example.bankcards.repository;

import com.example.bankcards.entity.CardBlockedRequestEntity;
import com.example.bankcards.enums.CardBlockedStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardBlockedRepository extends JpaRepository<CardBlockedRequestEntity, Long>, JpaSpecificationExecutor<CardBlockedRequestEntity> {

    Page<CardBlockedRequestEntity> findAllByStatus(CardBlockedStatus status, Pageable pageable);

    Optional<CardBlockedRequestEntity> findByNumber(UUID number);
}
