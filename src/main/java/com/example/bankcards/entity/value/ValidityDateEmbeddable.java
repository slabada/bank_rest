package com.example.bankcards.entity.value;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ValidityDateEmbeddable {
    private Integer yearValidity;
    private Integer monthValidity;
}
