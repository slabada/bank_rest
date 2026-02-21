package com.example.bankcards.domain.value;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Owner {
    private Long linkOwnerId;
    private String firstName;
    private String LastName;
}
