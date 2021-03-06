package com.interview.webcrawler.crawler;

import com.interview.webcrawler.DocumentRetriever;
import com.interview.webcrawler.PageDocument;
import com.interview.webcrawler.retriever.UrlRetriever;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ConcurrentCrawl extends Crawl {
    private final Logger logger = LoggerFactory.getLogger(ConcurrentCrawl.class);
    private final UrlRetriever urlRetriever;
    private final DocumentRetriever documentRetriever;

    private List<Future<List<String>>> futureWords = List.empty();

    public ConcurrentCrawl(DocumentRetriever documentRetriever, WordRetriever wordRetriever, UrlRetriever urlRetriever) {
        super(documentRetriever, wordRetriever);
        this.documentRetriever = documentRetriever;
        this.urlRetriever = urlRetriever;
    }

    @Override
    public List<String> crawl(String rootUrl) {
        saveWords(getWordsFromUrl(rootUrl));

        getWordsForAllUrlsInPage(rootUrl);

        for (Future<List<String>> f : futureWords) {
            saveWords(f.getOrElse(List.empty()));
        }

        return wordList;
    }

    private void getWordsForAllUrlsInPage(String rootUrl) {
        try {
            final PageDocument document = documentRetriever.getDocument(rootUrl);

            for (String url : getUniqueUrls(document)) {
                if(pageNotVisited(url)) {
                    logger.info("Crawling page: " + url); //TODO: add logging test
                    futureWords = futureWords.append(Future.of(() -> getWordsFromUrl(url)));
                    setPageAsVisited(url);
                } else {
                    logger.info("Skipping page: " + url);
                }
                getWordsForAllUrlsInPage(url);
            }
        } catch (IOException e) {
            logger.debug("Failed to fetch page for: " + rootUrl);
        }
    }

    private void setPageAsVisited(String url) {
        visitedUrls = visitedUrls.put(url, true);
    }

    private boolean pageNotVisited(String url) {
        return !visitedUrls.getOrElse(url, false);
    }

    private List<String> getUniqueUrls(PageDocument document) {
        return urlRetriever.retrieve(document).distinct();
    }

    private void saveWords(List<String> wordsFromUrl) {
        wordList = wordList.appendAll(wordsFromUrl);
    }
}
