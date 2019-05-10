package com.interview.webcrawler;

import com.interview.webcrawler.crawler.ConcurrentCrawl;
import com.interview.webcrawler.crawler.Crawl;
import com.interview.webcrawler.crawler.SingleCrawl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/webcrawler")
class WebCrawler {

    private final Crawl concurrentCrawl;
    private final Crawl singleCrawl;
    private MessageTransformer messageTransformer;

    public WebCrawler(SingleCrawl singleCrawl, ConcurrentCrawl concurrentCrawl, MessageTransformer messageTransformer) {
        this.concurrentCrawl = concurrentCrawl;
        this.singleCrawl = singleCrawl;
        this.messageTransformer = messageTransformer;
    }

    @RequestMapping(value = "/single-crawl", method = RequestMethod.GET)
    public List<Map> crawl(@RequestParam("url") String url) {
        return messageTransformer.transform(singleCrawl.crawl(url)).toJavaList();
    }

    @RequestMapping(value = "/concurrent-crawl", method = RequestMethod.GET)
    public List<Map> concurrentCrawl(@RequestParam("url") String url) {
        return messageTransformer.transform(concurrentCrawl.crawl(url)).toJavaList();
    }
}
