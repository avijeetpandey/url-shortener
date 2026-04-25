package com.avijeet.urlshortener.controller;

import com.avijeet.urlshortener.dto.UrlRequest;
import com.avijeet.urlshortener.dto.UrlResponse;
import com.avijeet.urlshortener.services.UrlShortenerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.IgnoreForbiddenApisErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UrlController {
    private final UrlShortenerService urlShortenerService;

    @PostMapping("api/v1/urls")
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody UrlRequest urlRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlShortenerService.shortenUrl(urlRequest));
    }

    @GetMapping("{shortUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortUrl) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
