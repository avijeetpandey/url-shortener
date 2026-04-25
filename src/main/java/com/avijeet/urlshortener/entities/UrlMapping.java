package com.avijeet.urlshortener.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_mapping", indexes = {
        @Index(name = "idx_short_url", columnList = "shortUrl", unique = true)
})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UrlMapping {
    @Id
    private Long id;

    @Column(nullable = false, unique = true, updatable = false, length = 15)
    private String shortUrl;

    @Column(nullable = false, length = 2048)
    private String originalUrl;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
