package com.interview.webcrawler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/webcrawler")
public class WebCrawler {

    private static final String MALFORMED_REQUEST_RESPONSE = "Invalid URL. Please make sure you include 'http://'";
    private UrlLoader urlLoader;
    private WordRetriever wordRetriever;

    public WebCrawler(UrlLoader urlLoader, WordRetriever wordRetriever) {
        this.urlLoader = urlLoader;
        this.wordRetriever = wordRetriever;
    }

    @RequestMapping(value = "/crawl", method = RequestMethod.GET)
    public ResponseEntity<String> crawl(@RequestParam("url") String url) {
        try {
            PageDocument pageDocument = new PageDocument(urlLoader.getPageDocument(url));
            return ResponseEntity.ok(wordRetriever.retrieveAllWords(pageDocument).asJava().toString());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MALFORMED_REQUEST_RESPONSE);
        }
    }
}
