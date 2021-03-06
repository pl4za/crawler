package com.interview.webcrawler.crawler;

import com.interview.webcrawler.DocumentRetriever;
import com.interview.webcrawler.PageDocument;
import com.interview.webcrawler.retriever.WordRetriever;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SingleCrawl.class)
public class SingleCrawlTest {
    @Autowired
    private
    SingleCrawl singleCrawl;

    @MockBean
    private DocumentRetriever documentRetriever;

    @MockBean
    private WordRetriever wordRetriever;

    private final String rootUrl = "https://www.website.co.uk";

    private PageDocument mockDocument(List<String> links) {
        PageDocument document = mock(PageDocument.class);
        when(document.getText()).thenReturn("rootPageContent");
        when(document.getUrls()).thenReturn(links);
        return document;
    }

    private void mockPage(List<String> urls) throws IOException {
        PageDocument rootDocument = mockDocument(urls);
        when(documentRetriever.getDocument("https://www.website.co.uk")).thenReturn(rootDocument);
        when(wordRetriever.retrieve(rootDocument)).thenReturn(List.of("rootPageContent"));
    }

    @Before
    public void setUp() {
        Mockito.reset(documentRetriever, wordRetriever);
    }

    @Test
    public void itCrawlsSinglePageInRootPage() throws Exception {
        mockPage(List.empty());

        String rootPageContent = "rootPageContent";
        assertEquals(List.of(rootPageContent), singleCrawl.crawl(rootUrl));
    }

    @Test
    public void itSkipsPage_WhenPageFetchFails() throws IOException {
        mockPage(List.empty());
        when(documentRetriever.getDocument(rootUrl)).thenThrow(new IOException());

        assertEquals(List.empty(), singleCrawl.crawl(rootUrl));
    }
}