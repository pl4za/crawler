package com.interview.webcrawler;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
class MessageTransformer {

    public List<Map> transform(List wordList) { //TODO: check assignments
        return wordList.map(word -> HashMap.of(
                "text", word,
                "value", wordList.count(innerWord -> innerWord.equals(word)))
                .toJavaMap())
                .distinct();
    }
}
