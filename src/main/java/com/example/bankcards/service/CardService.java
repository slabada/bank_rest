package com.example.bankcards.service;

import com.bank_rest.model.CardStatusDto;
import com.bank_rest.model.TranslationRequest;
import com.bank_rest.model.TranslationResponse;
import com.example.bankcards.domain.CardDomain;
import com.example.bankcards.enums.CardBlockedStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {

    Page<CardDomain> getAllCard(String search, Pageable pageable);

    Page<CardDomain> getCard(String search, Pageable pageable);

    CardDomain getCardByCardIdAndOwnerId(Long userId, Long cardId);

    TranslationResponse transfer(TranslationRequest translationRequest);

    Long cardStatus(CardStatusDto cardStatusDto);

    Long cardDelete(Long cardId);

    CardDomain cardCreate(Long userId);

    void processBlockCard(Long cardId, CardBlockedStatus status);
}
