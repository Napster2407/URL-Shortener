package com.example.demo.controller;
import com.example.demo.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/url")
public class UrlMappingController {
    private final UrlShortenerService urlShortenerService;
    @Autowired
    public UrlMappingController(UrlShortenerService urlShortenerService){
        this.urlShortenerService=urlShortenerService;
    }
    //This will be the API to shorten the URL
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam String longUrl){
        return urlShortenerService.shortenUrl(longUrl);
    }
    //This API will get original URL from a short Url
    @GetMapping("/{shortUrl}")
    public String getLongUrl(@PathVariable String shortUrl){
        return urlShortenerService.getLongUrl(shortUrl);
    }


}
