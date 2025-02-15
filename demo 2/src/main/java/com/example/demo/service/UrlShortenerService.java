package com.example.demo.service;

import com.example.demo.entity.UrlMapping;
import org.springframework.stereotype.Service;
import com.example.demo.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Service
public class UrlShortenerService {
    private final UrlMappingRepository urlMappingRepository;
    @Autowired
    public UrlShortenerService(UrlMappingRepository urlMappingRepository) {
        this.urlMappingRepository = urlMappingRepository;
    }
    public String shortenUrl(String longUrl) {
        // Check if the URL is already shortened
        Optional<UrlMapping> existingMapping = urlMappingRepository.findByLongUrl(longUrl);
        if (existingMapping.isPresent()) {
            return existingMapping.get().getShortUrl();
        }

        // Generate a short URL using Base64 encoding (first 8 characters)
        String encodedUrl = Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(longUrl.getBytes(StandardCharsets.UTF_8))
                .substring(0, 8);

        // Save the new mapping
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortUrl(encodedUrl);
        urlMappingRepository.save(urlMapping);

        return encodedUrl;
    }
    public String getLongUrl(String shortUrl){
        Optional<UrlMapping> mapping = urlMappingRepository.findByShortUrl(shortUrl);
        return mapping.map(UrlMapping::getLongUrl).orElse(null);
    }

}
