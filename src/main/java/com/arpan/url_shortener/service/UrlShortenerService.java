package com.arpan.url_shortener.service;

import com.arpan.url_shortener.dto.AnalyticsResponse;
import com.arpan.url_shortener.entity.UrlMapping;
import com.arpan.url_shortener.repo.UrlMappingRepository;
import com.arpan.url_shortener.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    @Autowired
    private  UrlMappingRepository repository;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public String shortenUrl(String longUrl) {

        Optional<UrlMapping> existing = repository.findByLongUrl(longUrl);

        if(existing.isPresent()){
            return existing.get().getShortCode();
        }

        UrlMapping mapping = UrlMapping.builder()
                .longUrl(longUrl)
                .clickCount(0L)
                .build();

        mapping = repository.save(mapping);

        String shortCode =
                Base62Encoder.encode(mapping.getId());

        mapping.setShortCode(shortCode);

        repository.save(mapping);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {

        String key = "url:" + shortCode;

        String cachedUrl = (String) redisTemplate.opsForValue().get(key);

        if (cachedUrl != null) {
            System.out.println("CACHE HIT");
            return cachedUrl;
        }

        System.out.println("CACHE MISS");

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new RuntimeException("Short URL not found"));
        mapping.setClickCount(
                mapping.getClickCount() + 1
        );

        System.out.println("Click Count = " + mapping.getClickCount());

        repository.save(mapping);

        redisTemplate.opsForValue().set(
                key,
                mapping.getLongUrl(),
                Duration.ofMinutes(10)
        );

        return mapping.getLongUrl();
    }

    public AnalyticsResponse getAnalytics(String shortCode) {

        UrlMapping mapping = repository.findByShortCode(shortCode)
                .orElseThrow(() ->
                        new RuntimeException("Short URL not found"));

        return new AnalyticsResponse(
                mapping.getShortCode(),
                mapping.getLongUrl(),
                mapping.getClickCount()
        );
    }
}
