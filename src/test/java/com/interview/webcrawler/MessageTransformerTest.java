package com.interview.webcrawler;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = MessageTransformer.class)
public class MessageTransformerTest {

    @Autowired
    private MessageTransformer messageTransformer;

    @Test
    public void itTransformstListOfWordsIntoWordCloudFormat() {
        List wordList = List.of("one", "two", "two");

        Map firstObject = HashMap.of("text", "one", "value", 1).toJavaMap();
        Map secondObject = HashMap.of("text", "two", "value", 2).toJavaMap();

        assertEquals(List.of(firstObject, secondObject), messageTransformer.transform(wordList));
    }
}