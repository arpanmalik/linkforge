package com.arpan.url_shortener.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
@Builder
public class AnalyticsResponse {

    private String shortCode;
    private String longUrl;
    private Long clickCount;
}