package com.arpan.url_shortener.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisRateLimiterService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public boolean allowRequest(String ip) {

        String key = "rate_limit:" + ip;

        Long count =
                redisTemplate.opsForValue().increment(key);

        if (count == 1) {
            redisTemplate.expire(
                    key,
                    Duration.ofMinutes(1)
            );
        }

        System.out.println("IP = " + ip + ", Count = " + count);
        return count <= 10;
    }
}
