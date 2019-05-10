package com.interview.webcrawler;

import io.vavr.collection.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PageDocumentTest {

    @Test
    public void itGetsTextFromPage_WhenPageHasText() {
        String htmlPageWithText = "<p> All your base are belong to us </p>";
        PageDocument pageDocument = new PageDocument(htmlPageWithText);

        assertEquals(pageDocument.getText(), "All your base are belong to us");
    }

    @Test
    public void itGetsNoTextBackFromPage_WhenPageHasNoText() {
        String emptyHtmlPage = "<p> </p>";
        PageDocument pageDocument = new PageDocument(emptyHtmlPage);

        assertEquals(pageDocument.getText(), "");
    }

    @Test
    public void itGetsUrlsFromPage_WhenPageHasUrls() {
        String htmlPageWithUrls = "<div>" +
                "<a href='https://www.example.com/url1.html'> valid url1 </a> " +
                "<a href='https://www.example.com/url2.html'> valid url2 </a>" +
                "<a> valid url2 </a>" +
                "</div>";
        PageDocument pageDocument = new PageDocument(htmlPageWithUrls);

        assertEquals(List.of("https://www.example.com/url1.html", "https://www.example.com/url2.html"), pageDocument.getUrls());
    }
}