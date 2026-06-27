package com.arpan.url_shortener.config;

import com.arpan.url_shortener.service.RedisRateLimiterService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisHealthCheck {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisRateLimiterService redisRateLimiterService;

    @PostConstruct
    public void init() {

        redisTemplate.opsForValue()
                .set("test", "hello redis");

        System.out.println(
                redisTemplate.opsForValue().get("test")
        );
    }
}