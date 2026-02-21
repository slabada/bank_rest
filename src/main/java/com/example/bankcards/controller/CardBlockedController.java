package com.example.bankcards.controller;

import com.bank_rest.api.CardBlockedApi;
import com.bank_rest.model.CardBlockedRequestDto;
import com.bank_rest.model.CardBlockedResponseDto;
import com.bank_rest.model.PageCardBlockedRequestDto;
import com.bank_rest.model.ProcessBlockDto;
import com.example.bankcards.service.CardBlockedService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CardBlockedController implements CardBlockedApi {

    private final CardBlockedService cardBlockedService;

    @Override
    public ResponseEntity<PageCardBlockedRequestDto> getAllBlockedRequest(Pageable pageable) {
        Page<CardBlockedRequestDto> page = cardBlockedService.getAllCardBlockedRequest(pageable);
        return ResponseEntity.ok().body(new PageCardBlockedRequestDto()
                .page(page.getNumber())
                .size(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .content(page.getContent())
        );
    }

    @Override
    public ResponseEntity<ProcessBlockDto> processBlockRequest(ProcessBlockDto processBlockDto) {
        ProcessBlockDto processBlock = cardBlockedService.processBlock(processBlockDto);
        return ResponseEntity.ok().body(processBlock);
    }

    @Override
    public ResponseEntity<CardBlockedResponseDto> requestBlock(Long cardId) {
        String applicationNumber = String.valueOf(cardBlockedService.requestBlock(cardId));
        return ResponseEntity.ok().body(new CardBlockedResponseDto().applicationNumber(applicationNumber));
    }
}
