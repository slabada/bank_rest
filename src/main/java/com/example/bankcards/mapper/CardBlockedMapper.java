package com.example.bankcards.mapper;

import com.bank_rest.model.CardBlockedRequestDto;
import com.bank_rest.model.StatusCardBlockedEnum;
import com.example.bankcards.entity.CardBlockedRequestEntity;
import com.example.bankcards.enums.CardBlockedStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Mapper(componentModel = "spring")
public interface CardBlockedMapper {

    @Mapping(target = "statusCard", source = "status", qualifiedByName = "status")
    @Mapping(target = "cardId", source = "linkCardId")
    @Mapping(target = "createDate", source = "createDate", qualifiedByName = "createDate")
    CardBlockedRequestDto toDto(CardBlockedRequestEntity entity);

    @Named("status")
    default StatusCardBlockedEnum status(CardBlockedStatus status) {
        if (status == null) return null;
        return switch (status) {
            case APPROVED -> StatusCardBlockedEnum.APPROVED;
            case REJECTED -> StatusCardBlockedEnum.REJECTED;
            case PENDING -> StatusCardBlockedEnum.PENDING;
        };
    }

    @Named("createDate")
    default OffsetDateTime createDate(LocalDateTime value) {
        return value == null ? null : value.atOffset(ZoneOffset.UTC);
    }
}
