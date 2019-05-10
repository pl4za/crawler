package com.interview.webcrawler;

import com.interview.webcrawler.crawler.ConcurrentCrawl;
import com.interview.webcrawler.crawler.SingleCrawl;
import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WebCrawler.class)
public class WebCrawlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConcurrentCrawl concurrentCrawl;

    @MockBean
    private SingleCrawl singleCrawl;

    @MockBean
    private MessageTransformer messageTransformer;

    private final String expected = "[{\"text\":\"one\",\"value\":1},{\"text\":\"two\",\"value\":2}]";

    @Before
    public void setUp() {
        Map firstObject = HashMap.of("text", "one", "value", 1).toJavaMap();
        Map secondObject = HashMap.of("text", "two", "value", 2).toJavaMap();
        when(messageTransformer.transform(any())).thenReturn(List.of(firstObject, secondObject));
    }

    @Test
    public void itReturnsAllWordsForSingleCrawl() throws Exception {
        final String rootUrl = "https://www.website.co.uk";

        mockMvc.perform(get("/webcrawler/single-crawl?url=" + rootUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }

    @Test
    public void itReturnsAllWordsForConcurrentCrawl() throws Exception {
        final String rootUrl = "https://www.website.co.uk";

        mockMvc.perform(get("/webcrawler/concurrent-crawl?url=" + rootUrl))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}