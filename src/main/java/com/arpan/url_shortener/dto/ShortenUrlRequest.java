package com.arpan.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenUrlRequest {
    @NotBlank(message = "URL can not be empty")
    private String longUrl;
}
