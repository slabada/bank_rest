package com.example.bankcards.controller;

import com.bank_rest.api.CardApi;
import com.bank_rest.model.CardDto;
import com.bank_rest.model.CardStatusDto;
import com.bank_rest.model.CreateCardDto;
import com.bank_rest.model.PageResponseCardDto;
import com.bank_rest.model.ResponseId;
import com.bank_rest.model.TranslationRequest;
import com.bank_rest.model.TranslationResponse;
import com.example.bankcards.domain.CardDomain;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CardController implements CardApi {

    private final CardService cardService;
    private final CardMapper cardMapper;

    @Override
    public ResponseEntity<CardDto> cardCreate(CreateCardDto createCardDto) {
        CardDomain cardDomain = cardService.cardCreate(createCardDto.getUserId());
        CardDto dto = cardMapper.toDtoAdmin(cardDomain);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<ResponseId> cardDelete(Long cardId) {
        Long deleteCardId = cardService.cardDelete(cardId);
        return ResponseEntity.ok().body(new ResponseId().id(deleteCardId));
    }

    @Override
    public ResponseEntity<ResponseId> cardStatus(CardStatusDto cardStatusDto) {
        Long cardId = cardService.cardStatus(cardStatusDto);
        return ResponseEntity.ok().body(new ResponseId().id(cardId));
    }

    @Override
    public ResponseEntity<PageResponseCardDto> getAllCards(String search, Pageable pageable) {
        Page<CardDomain> allCard = cardService.getAllCard(search, pageable);
        List<CardDto> content = allCard.getContent().stream()
                .map(cardMapper::toDtoAdmin)
                .toList();
        return ResponseEntity.ok().body(new PageResponseCardDto()
                .page(allCard.getNumber())
                .size(allCard.getSize())
                .totalPages(allCard.getTotalPages())
                .totalElements(allCard.getTotalElements())
                .content(content));
    }

    @Override
    public ResponseEntity<PageResponseCardDto> getMyCards(String search, Pageable pageable) {
        Page<CardDomain> card = cardService.getCard(search, pageable);
        List<CardDto> content = card.getContent().stream()
                .map(cardMapper::toDto)
                .toList();
        return ResponseEntity.ok().body(new PageResponseCardDto()
                .page(card.getNumber())
                .size(card.getSize())
                .totalPages(card.getTotalPages())
                .totalElements(card.getTotalElements())
                .content(content));
    }

    @Override
    public ResponseEntity<TranslationResponse> translation(TranslationRequest translationRequest) {
        TranslationResponse transfer = cardService.transfer(translationRequest);
        return ResponseEntity.ok().body(transfer);
    }
}
