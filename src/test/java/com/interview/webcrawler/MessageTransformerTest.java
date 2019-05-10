package com.interview.webcrawler;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MessageTransformer.class)
public class MessageTransformerTest {

    @Autowired
    MessageTransformer messageTransformer;

    /*
    const words = [
      { text: "one", value: 1 },
      { text: "two", value: 2 },
      { text: "three", value: 3 }
    ];
     */
    @Test
    public void itTransformstListOfWordsIntoWordCloudFormat() {
        List wordList = List.of("one", "two", "two");

        Map firstObject = HashMap.of("text", "one", "value", 1);
        Map secondObject = HashMap.of("text", "two", "value", 2);

        assertEquals(List.of(firstObject, secondObject), messageTransformer.transform(wordList));
    }
}