package com.example.bankcards.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerationNumberCardUtil {

    private static final Random random = new Random();

    public static String generateCardNumber() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(String.format("%04d", random.nextInt(10000))); // 4 цифры
            if (i < 3) sb.append("-");
        }
        return sb.toString();
    }
}
