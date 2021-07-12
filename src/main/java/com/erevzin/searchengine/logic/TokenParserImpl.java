package com.erevzin.searchengine.logic;

import org.springframework.stereotype.Service;

@Service
public class TokenParserImpl implements TokenParser {

    @Override
    public String parseToken(String token) {
        //TODO: clean from characters like ',', '.', '(', ')', '!', '?' etc. - parse the token to index only pure words
        return token;
    }
}
