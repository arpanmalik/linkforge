package com.arpan.url_shortener.repo;

import com.arpan.url_shortener.entity.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    Optional<UrlMapping> findByShortCode(String shortCode);
    Optional<UrlMapping> findByLongUrl(String longUrl);
}
