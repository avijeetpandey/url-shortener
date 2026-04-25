package com.avijeet.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UrlRequest {
    @NotBlank(message = "URL cannot be blank")
    @URL(message = "Must be a valid URL")
    private String originalUrl;
}
