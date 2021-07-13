package com.erevzin.searchengine.logic.parsers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TokenParserImplTest {

    private TokenParser tokenParser;

    @BeforeEach
    public void init(){
        tokenParser = new TokenParserImpl();
    }

    @Test
    void parseTokenWithDot() {
        String actual = tokenParser.parseToken("cat.");
        assertEquals("cat", actual);
    }

    @Test
    void parseTokenUpperCase() {
        String actual = tokenParser.parseToken("CAT");
        assertEquals("cat", actual);
    }
}