package com.erevzin.searchengine.logic.parsers;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;

import static com.erevzin.searchengine.utilities.Patterns.charactersToExcludePattern;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
public class TokenParserImpl implements TokenParser {

    @Override
    public String parseToken(String token) {
        Matcher idContentSeparatorMatcher = charactersToExcludePattern.matcher(token);
        while (idContentSeparatorMatcher.find()) {
            token = token.replace(idContentSeparatorMatcher.group(), EMPTY);
        }
        return token.toLowerCase();
    }
}
