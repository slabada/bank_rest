package com.example.bankcards.entity;

import com.example.bankcards.entity.value.MoneyEmbeddable;
import com.example.bankcards.entity.value.NumberCardEmbeddable;
import com.example.bankcards.entity.value.OwnerEmbeddable;
import com.example.bankcards.entity.value.ValidityDateEmbeddable;
import com.example.bankcards.enums.StatusCardEnum;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "card")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private NumberCardEmbeddable numberCard;
    @Embedded
    private OwnerEmbeddable owner;
    @Embedded
    private ValidityDateEmbeddable validityDate;
    @Enumerated(EnumType.STRING)
    private StatusCardEnum status;
    @Embedded
    private MoneyEmbeddable balance;
}
