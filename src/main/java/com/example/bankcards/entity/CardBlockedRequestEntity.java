package com.example.bankcards.entity;

import com.example.bankcards.enums.CardBlockedStatus;
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

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "CardBlockedRequest")
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CardBlockedRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID number;
    private Long linkCardId;
    @Enumerated(EnumType.STRING)
    private CardBlockedStatus status;
    private LocalDateTime createDate;
}
