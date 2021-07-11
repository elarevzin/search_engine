package com.erevzin.searchengine.logic;

import org.springframework.stereotype.Service;

@Service
public class TokenParserImpl implements TokenParser {

    @Override
    public String parseToken(String token) {
        return token;
    }
}
