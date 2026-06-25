package com.arpan.url_shortener.service;

import com.arpan.url_shortener.entity.UrlMapping;
import com.arpan.url_shortener.repo.UrlMappingRepository;
import com.arpan.url_shortener.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlShortenerService {
    @Autowired
    private  UrlMappingRepository repository;

    public String shortenUrl(String longUrl) {

        Optional<UrlMapping> existing = repository.findByLongUrl(longUrl);

        if(existing.isPresent()){
            return existing.get().getShortCode();
        }

        UrlMapping mapping = UrlMapping.builder()
                .longUrl(longUrl)
                .build();

        mapping = repository.save(mapping);

        String shortCode =
                Base62Encoder.encode(mapping.getId());

        mapping.setShortCode(shortCode);

        repository.save(mapping);

        return shortCode;
    }

    public String getOriginalUrl(String shortCode) {

        return repository.findByShortCode(shortCode)
                .map(UrlMapping::getLongUrl)
                .orElseThrow(() ->
                        new RuntimeException("Short URL not found"));
    }
}
