package com.example.bankcards.service;

import com.bank_rest.model.CardBlockedRequestDto;
import com.bank_rest.model.ProcessBlockDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CardBlockedService {

    UUID requestBlock(Long cardId);

    Page<CardBlockedRequestDto> getAllCardBlockedRequest(Pageable pageable);

    ProcessBlockDto processBlock(ProcessBlockDto processBlockDto);
}
