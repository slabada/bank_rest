package com.example.bankcards.service;

import com.bank_rest.model.CardBlockedRequestDto;
import com.bank_rest.model.ProcessBlockDto;
import com.bank_rest.model.StatusCardBlockedEnum;
import com.example.bankcards.domain.CardDomain;
import com.example.bankcards.entity.CardBlockedRequestEntity;
import com.example.bankcards.enums.CardBlockedStatus;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.mapper.CardBlockedMapper;
import com.example.bankcards.repository.CardBlockedRepository;
import com.example.bankcards.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CardBlockedServiceImpl implements CardBlockedService {

    private final CardService cardService;
    private final CardBlockedRepository cardBlockedRepository;
    private final CardBlockedMapper cardBlockedMapper;
    private final SecurityUtil securityUtil;

    @Override
    @Transactional
    public UUID requestBlock(Long cardId) {
        Long currentUserId = securityUtil.getUserSecurity().getId();
        CardDomain card = cardService.getCardByCardIdAndOwnerId(currentUserId, cardId);
        CardBlockedRequestEntity entity = CardBlockedRequestEntity.builder()
                .linkCardId(card.getId())
                .status(CardBlockedStatus.PENDING)
                .createDate(LocalDateTime.now())
                .build();
        CardBlockedRequestEntity save = cardBlockedRepository.save(entity);
        return save.getNumber();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CardBlockedRequestDto> getAllCardBlockedRequest(Pageable pageable) {
        Page<CardBlockedRequestEntity> page = cardBlockedRepository.findAllByStatus(CardBlockedStatus.PENDING, pageable);
        return page.map(cardBlockedMapper::toDto);
    }

    @Override
    public ProcessBlockDto processBlock(ProcessBlockDto processBlockDto) {
        CardBlockedRequestEntity blockedRequest = cardBlockedRepository.findByNumber(processBlockDto.getApplicationNumber())
                .orElseThrow(CardException.NotFoundCardException::new);
        CardBlockedStatus status = CardBlockedStatus.fromString(processBlockDto.getStatus().name());
        cardService.processBlockCard(blockedRequest.getLinkCardId(), status);
        blockedRequest.setStatus(status);
        CardBlockedRequestEntity save = cardBlockedRepository.save(blockedRequest);
        return new ProcessBlockDto()
                .applicationNumber(save.getNumber())
                .status(StatusCardBlockedEnum.fromValue(save.getStatus().name()));
    }
}
