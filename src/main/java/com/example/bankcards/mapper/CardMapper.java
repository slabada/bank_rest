package com.example.bankcards.mapper;

import com.bank_rest.model.CardDto;
import com.bank_rest.model.StatusCardEnum;
import com.example.bankcards.domain.CardDomain;
import com.example.bankcards.domain.value.Money;
import com.example.bankcards.domain.value.NumberCard;
import com.example.bankcards.domain.value.Owner;
import com.example.bankcards.domain.value.ValidityDate;
import com.example.bankcards.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDomain toDomain(CardEntity entity);

    CardEntity toEntity(CardDomain domain);

    @Mapping(target = "validityDate", source = "validityDate", qualifiedByName = "validityDate")
    @Mapping(target = "fullName", source = "owner", qualifiedByName = "fullName")
    @Mapping(target = "numberCard", source = "numberCard", qualifiedByName = "toNumber")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "moneyToDouble")
    @Mapping(target = "status", source = "status", qualifiedByName = "status")
    CardDto toDto(CardDomain domain);

    @Mapping(target = "validityDate", source = "validityDate", qualifiedByName = "validityDate")
    @Mapping(target = "fullName", source = "owner", qualifiedByName = "fullName")
    @Mapping(target = "numberCard", source = "numberCard", qualifiedByName = "toNumberAdmin")
    @Mapping(target = "balance", source = "balance", qualifiedByName = "moneyToDouble")
    @Mapping(target = "status", source = "status", qualifiedByName = "status")
    CardDto toDtoAdmin(CardDomain domain);

    @Named("validityDate")
    default String validateDate(ValidityDate validityDate) {
        return validityDate != null ? validityDate.getMonthValidity() + "/" +  validityDate.getYearValidity() : null;
    }

    @Named("fullName")
    default String fullName(Owner owner) {
        return owner != null ? owner.getFirstName() + " " + owner.getLastName() : null;
    }

    @Named("toNumber")
    default String toNumber(NumberCard numberCard) {
        return numberCard != null ? numberCard.getNumber() : null;
    }

    @Named("toNumberAdmin")
    default String toNumberAdmin(NumberCard numberCard) {
        return numberCard != null ? numberCard.getNumberMask() : null;
    }

    @Named("moneyToDouble")
    default Double moneyToDouble(Money money) {
        return money != null ? money.getAmount().doubleValue() : null;
    }

    @Named("status")
    default StatusCardEnum status(com.example.bankcards.enums.StatusCardEnum status) {
        if (status == null) return null;
        return switch (status) {
            case ACTION -> StatusCardEnum.ACTION;
            case BLOCK -> StatusCardEnum.BLOCKED;
            case EXPIRED -> StatusCardEnum.EXPIRED;
        };
    }
}
