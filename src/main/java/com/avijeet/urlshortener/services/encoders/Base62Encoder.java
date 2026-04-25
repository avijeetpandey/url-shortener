package com.avijeet.urlshortener.services.encoders;

import org.springframework.stereotype.Component;

@Component
public class Base62Encoder {
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int BASE = ALLOWED_CHARACTERS.length();

    public String encode(long input) {
        StringBuilder encodedString = new StringBuilder();

        if (input == 0) {
            return String.valueOf(ALLOWED_CHARACTERS.charAt(0));
        }

        while (input > 0) {
            encodedString.append(ALLOWED_CHARACTERS.charAt((int) (input % BASE)));
            input /= BASE;
        }

        return encodedString.reverse().toString();
    }
}
