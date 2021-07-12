package com.erevzin.searchengine.logic.parsers;

import com.erevzin.searchengine.model.WikiPageDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WikiPageParserImplTest {

    private WikiPageParser wikiPageParser;

    @Mock
    private TokenParser tokenParser;

    @BeforeEach
    public void init(){
        tokenParser = new TokenParserImpl();
        wikiPageParser = new WikiPageParserImpl(tokenParser);
    }

    @Test
    void parseLine() {
        WikiPageDTO actual = wikiPageParser.parseLine("{\"13603179\": \"France is a Country.\"}");
        assertEquals("13603179", actual.getWikiPageId());
        assertEquals("France is a Country.", actual.getContent());
    }

    @Test
    void parseTokens() {
        List<String> actual = wikiPageParser.parseTokens("France is a Country.");
        assertEquals(4, actual.size());
        assertTrue(actual.contains("france"));
        assertTrue(actual.contains("is"));
        assertTrue(actual.contains("a"));
        assertTrue(actual.contains("country"));



    }
}