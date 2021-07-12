package com.erevzin.searchengine.logic.parsers;

import com.erevzin.searchengine.model.WikiPageDTO;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.erevzin.searchengine.utilities.Patterns.idContentSeparatorPattern;
import static com.erevzin.searchengine.utilities.Patterns.spacesPattern;

@Service
public class WikiPageParserImpl implements WikiPageParser {

    private final TokenParser tokenParser;

    public WikiPageParserImpl(TokenParser tokenParser) {
        this.tokenParser = tokenParser;
    }

    @Override
    public WikiPageDTO parseLine(String line){
        //split id from content
        Matcher idContentSeparatorMatcher = idContentSeparatorPattern.matcher(line);
        if(!idContentSeparatorMatcher.find()){
            System.out.println("NO WIKI PAGE ID FOR THIS LINE. APPEND THE CONTENT TO THE PREVIOUS WIKI PAGE !!! "); //TODO: change to take care of this special case
            return new WikiPageDTO("NO WIKI PAGE ID", line);
        }

        String wikiPageId = line.substring(2,idContentSeparatorMatcher.start());
        String wikiPageContent =  line.substring(idContentSeparatorMatcher.end()+2, line.length()-2);

        return new WikiPageDTO(wikiPageId, wikiPageContent);
    }

    public List<String> parseTokens(String wikiPageContent){
        List<String> tokens = Arrays.asList(spacesPattern.split(wikiPageContent));
        return tokens.stream().map(tokenParser::parseToken).collect(Collectors.toList());
    }

}
