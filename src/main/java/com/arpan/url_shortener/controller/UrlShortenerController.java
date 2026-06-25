package com.arpan.url_shortener.controller;

import com.arpan.url_shortener.dto.ShortenUrlRequest;
import com.arpan.url_shortener.dto.ShortenUrlResponse;
import com.arpan.url_shortener.service.UrlShortenerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class UrlShortenerController {
    @Autowired
    private UrlShortenerService service;

    @PostMapping("/shorten")
    public ShortenUrlResponse shorten(
           @Valid @RequestBody ShortenUrlRequest request) {

        String code =
                service.shortenUrl(request.getLongUrl());

        return new ShortenUrlResponse(
                "http://localhost:8080/" + code
        );
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(
            @PathVariable String shortCode) {

        String originalUrl =
                service.getOriginalUrl(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
