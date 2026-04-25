package com.avijeet.urlshortener.services;

import com.avijeet.urlshortener.dto.UrlRequest;
import com.avijeet.urlshortener.dto.UrlResponse;
import com.avijeet.urlshortener.entities.UrlMapping;
import com.avijeet.urlshortener.exceptions.UrlNotFoundException;
import com.avijeet.urlshortener.repository.UrlRepository;
import com.avijeet.urlshortener.services.encoders.Base62Encoder;
import com.avijeet.urlshortener.services.generators.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    private final UrlRepository urlRepository;
    private final IdGenerator idGenerator;
    private final Base62Encoder encoder;
    private final RedisTemplate<String, String> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "url:";
    private static final Duration CACHE_DURATION = Duration.ofDays(7);

    public UrlResponse shortenUrl(UrlRequest urlRequest) {
        long id = idGenerator.generateId();
        String shortUrl = encoder.encode(id);

        UrlMapping urlMapping = UrlMapping.builder()
                .id(id)
                .shortUrl(shortUrl)
                .originalUrl(urlRequest.getOriginalUrl())
                .createdAt(LocalDateTime.now())
                .build();

        return new UrlResponse(shortUrl, urlRequest.getOriginalUrl());
    }

    public String getOriginalUrl(String shortUrl) {
        String cachedUrl = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + shortUrl);
        if (cachedUrl != null) {
            return cachedUrl;
        }

        UrlMapping urlMapping = urlRepository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found: " + shortUrl));

        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + shortUrl, urlMapping.getOriginalUrl(), CACHE_DURATION);
        return urlMapping.getOriginalUrl();
    }
}
