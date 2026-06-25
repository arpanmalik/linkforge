package com.arpan.url_shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ShortenUrlResponse {
    private String shortUrl;
}
