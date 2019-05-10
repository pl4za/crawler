package com.interview.webcrawler.crawler;

import com.interview.webcrawler.DocumentRetriever;
import com.interview.webcrawler.PageDocument;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class Crawl {
    private final Logger logger = LoggerFactory.getLogger(Crawl.class);

    private final DocumentRetriever documentRetriever;
    private final WordRetriever wordRetriever;
    List<String> wordList = List.empty();

    Crawl(DocumentRetriever documentRetriever, WordRetriever wordRetriever) {
        this.documentRetriever = documentRetriever;
        this.wordRetriever = wordRetriever;
    }

    public abstract List<String> crawl(String rootUrl);

    List<String> getWordsFromUrl(String rootUrl) {
        try {
            logger.info("Crawling page: " + rootUrl);
            PageDocument rootDocument = documentRetriever.getDocument(rootUrl);
            return wordRetriever.retrieve(rootDocument);
        } catch (IOException e) {
            logger.debug("Failed to fetch page for: " + rootUrl);
            return List.empty();
        }
    }
}
