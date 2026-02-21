package com.example.bankcards.service;

import com.bank_rest.model.CardStatusDto;
import com.bank_rest.model.TranslationRequest;
import com.bank_rest.model.TranslationResponse;
import com.example.bankcards.domain.CardDomain;
import com.example.bankcards.domain.UserDomain;
import com.example.bankcards.domain.value.Owner;
import com.example.bankcards.entity.CardEntity;
import com.example.bankcards.entity.TransferEntity;
import com.example.bankcards.entity.value.MoneyEmbeddable;
import com.example.bankcards.entity.value.NumberCardEmbeddable;
import com.example.bankcards.entity.value.OwnerEmbeddable;
import com.example.bankcards.entity.value.ValidityDateEmbeddable;
import com.example.bankcards.enums.CardBlockedStatus;
import com.example.bankcards.enums.StatusCardEnum;
import com.example.bankcards.exception.CardException;
import com.example.bankcards.exception.TranslationException;
import com.example.bankcards.mapper.CardMapper;
import com.example.bankcards.repository.CardRepository;
import com.example.bankcards.repository.TransferRepository;
import com.example.bankcards.util.GenerationNumberCardUtil;
import com.example.bankcards.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final TransferRepository transferRepository;

    private final UserService userService;

    private final CardMapper cardMapper;

    private final SecurityUtil securityUtil;

    @Value("${validateCardYear}")
    private Integer validateCardYear;

    @Override
    @Transactional(readOnly = true)
    public Page<CardDomain> getAllCard(String search, Pageable pageable) {
        Specification<CardEntity> spec = Specification.where(null);
        if (search != null && !search.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("numberCard").get("number")), "%" + search.toLowerCase() + "%")
            ));
        }

        Page<CardEntity> page = cardRepository.findAll(spec, pageable);

        Set<Long> ownerIds = page.stream()
                .map(CardEntity::getOwner)
                .map(OwnerEmbeddable::getLinkOwnerId)
                .collect(Collectors.toSet());

        List<UserDomain> users = userService.findUserByIdAll(ownerIds);

        Map<Long, UserDomain> userMap = users.stream()
                .collect(Collectors.toMap(UserDomain::getId, u -> u));

        return page.map(entity -> {
            UserDomain user = userMap.get(entity.getOwner().getLinkOwnerId());
            Owner owner = new Owner(
                    entity.getOwner().getLinkOwnerId(),
                    user.getFirstName(),
                    user.getLastName()
            );
            CardDomain domain = cardMapper.toDomain(entity);
            domain.setOwner(owner);
            return domain;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CardDomain> getCard(String search, Pageable pageable) {
        Long currentUserId = securityUtil.getUserSecurity().getId();

        Specification<CardEntity> spec = Specification.where(
                (root, query, cb) -> cb.equal(root.get("owner").get("linkOwnerId"), currentUserId)
        );

        if (search != null && !search.isBlank()) {
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("numberCard").get("number")), "%" + search.toLowerCase() + "%")
            ));
        }

        Page<CardEntity> page = cardRepository.findAll(spec, pageable);

        Set<Long> ownerIds = page.stream()
                .map(CardEntity::getOwner)
                .map(OwnerEmbeddable::getLinkOwnerId)
                .collect(Collectors.toSet());

        List<UserDomain> users = userService.findUserByIdAll(ownerIds);

        Map<Long, UserDomain> userMap = users.stream()
                .collect(Collectors.toMap(UserDomain::getId, u -> u));

        return page.map(entity -> {
            UserDomain user = userMap.get(entity.getOwner().getLinkOwnerId());
            Owner owner = new Owner(
                    entity.getOwner().getLinkOwnerId(),
                    user.getFirstName(),
                    user.getLastName()
            );
            CardDomain domain = cardMapper.toDomain(entity);
            domain.setOwner(owner);
            return domain;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public CardDomain getCardByCardIdAndOwnerId(Long userId, Long cardId) {
        CardEntity entity = cardRepository.findByIdAndOwner_LinkOwnerId(cardId, userId)
                .orElseThrow(CardException.NotFoundCardException::new);
        return cardMapper.toDomain(entity);
    }

    @Override
    @Transactional
    public TranslationResponse transfer(TranslationRequest translationRequest) {

        Optional<TransferEntity> byRequestId = transferRepository.findByRequestId(translationRequest.getRequestId());
        
        if(byRequestId.isPresent()) {
            var transfer = byRequestId.get();
            return new TranslationResponse()
                    .fromCard(transfer.getFromCard())
                    .toCard(transfer.getToCard())
                    .count(transfer.getAmount());
        }

        Long currentUserId = securityUtil.getUserSecurity().getId();

        BigDecimal count = translationRequest.getCount();

        String fromCard = translationRequest.getFromCard();
        String toCard = translationRequest.getToCard();

        if (fromCard.equals(toCard)) {
            throw new TranslationException.ConflictNumberCardException();
        }

        CardEntity from = cardRepository.findByOwner_LinkOwnerIdAndNumberCard_Number(currentUserId, fromCard)
                .orElseThrow(CardException.NotFoundCardException::new);
        CardDomain fromDomain = cardMapper.toDomain(from);

        CardEntity to = cardRepository.findByOwner_LinkOwnerIdAndNumberCard_Number(currentUserId, toCard)
                .orElseThrow(CardException.NotFoundCardException::new);
        CardDomain toDomain = cardMapper.toDomain(to);

        if (!(from.getStatus().equals(StatusCardEnum.ACTION)) || !(to.getStatus().equals(StatusCardEnum.ACTION))) {
            throw new TranslationException.BadRequestCardStatusException();
        }

        fromDomain.getBalance().withdraw(count);
        toDomain.getBalance().deposit(count);

        from = cardMapper.toEntity(fromDomain);
        to = cardMapper.toEntity(toDomain);

        cardRepository.saveAll(List.of(from, to));

        saveTransfer(translationRequest, fromCard, toCard, count);

        return new TranslationResponse()
                .fromCard(from.getNumberCard().getNumber())
                .toCard(to.getNumberCard().getNumber())
                .count(count);
    }

    @Override
    @Transactional
    public Long cardStatus(CardStatusDto cardStatusDto) {
        CardEntity entity = cardRepository.findById(cardStatusDto.getId())
                .orElseThrow(CardException.NotFoundCardException::new);
        entity.setStatus(StatusCardEnum.fromString(cardStatusDto.getCardStatus().getValue()));
        cardRepository.save(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public Long cardDelete(Long cardId) {
        CardEntity entity = cardRepository.findById(cardId)
                .orElseThrow(CardException.NotFoundCardException::new);
        cardRepository.delete(entity);
        return entity.getId();
    }

    @Override
    @Transactional
    public CardDomain cardCreate(Long userId) {
        UserDomain user = userService.findUserById(userId);
        CardEntity card = createCard(userId);
        CardEntity save = cardRepository.save(card);
        CardDomain domain = cardMapper.toDomain(save);
        domain.setOwner(new Owner(userId, user.getFirstName(), user.getLastName()));
        return domain;
    }

    @Override
    @Transactional
    public void processBlockCard(Long cardId, CardBlockedStatus status) {
        CardEntity card = cardRepository.findById(cardId)
                .orElseThrow(CardException.NotFoundCardException::new);
        switch (status) {
            case APPROVED -> card.setStatus(StatusCardEnum.BLOCK);
            case REJECTED, PENDING -> card.setStatus(StatusCardEnum.ACTION);
        }
        cardRepository.save(card);
    }

    private CardEntity createCard(Long userId) {
        LocalDate today = LocalDate.now();
        Integer dayOfMonth = today.getMonthValue();
        Integer year = today.getYear() + validateCardYear;
        var numberCard = new NumberCardEmbeddable(GenerationNumberCardUtil.generateCardNumber());
        var owner = new OwnerEmbeddable(userId);
        var validateDate = new ValidityDateEmbeddable(year, dayOfMonth);
        var money = new MoneyEmbeddable(BigDecimal.ZERO);
        return CardEntity.builder()
                .numberCard(numberCard)
                .owner(owner)
                .validityDate(validateDate)
                .status(StatusCardEnum.ACTION)
                .balance(money)
                .build();
    }

    private void saveTransfer(TranslationRequest translationRequest, String fromCard, String toCard, BigDecimal count) {
        TransferEntity transfer = TransferEntity.builder()
                .requestId(translationRequest.getRequestId())
                .fromCard(fromCard)
                .toCard(toCard)
                .amount(count)
                .createdAt(LocalDateTime.now())
                .build();
        transferRepository.save(transfer);
    }
}
