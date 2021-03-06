package com.interview.webcrawler;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DocumentRetriever {

    private final UrlLoader urlLoader;

    public DocumentRetriever(UrlLoader urlLoader) {
        this.urlLoader = urlLoader;
    }

    public PageDocument getDocument(String url) throws IOException {
        return new PageDocument(getHtmlPage(url));
    }

    //TODO: Add memoization
    private String getHtmlPage(String rootUrl) throws IOException {
        return urlLoader.getHtmlPage(rootUrl);
    }
}
