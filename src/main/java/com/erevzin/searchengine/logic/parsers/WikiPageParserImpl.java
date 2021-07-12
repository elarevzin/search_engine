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


//    public String decodeUtf16(String wikiPageContent) {
//        Matcher utf16StartMatcher = utf16StartPattern.matcher(wikiPageContent);
//        if(!utf16StartMatcher.find()){
//            return wikiPageContent;
//        }
//
//        if(utf16StartMatcher.end()+4 >= wikiPageContent.length()-1){
//            return wikiPageContent;
//        }
//        String utf16StrToDecode = wikiPageContent.substring(utf16StartMatcher.start(), utf16StartMatcher.end()+3);
//        wikiPageContent.replace(utf16StrToDecode, decode(wikiPageContent, utf16StrToDecode));
//
//        while (utf16StartMatcher.find()) {
//            if(utf16StartMatcher.end()+4 >= wikiPageContent.length()-1){
//                return wikiPageContent;
//            }
//            utf16StrToDecode = wikiPageContent.substring(utf16StartMatcher.start(), utf16StartMatcher.end()+3);
//            wikiPageContent = wikiPageContent.replace(utf16StrToDecode, decode(wikiPageContent, utf16StrToDecode));
//        }
//        return wikiPageContent;
//    }
//
//    private String decode(String wikiPageContent, String utf16StrToDecode) {
//        String decoded = utf16StrToDecode;
//        try {
//            byte[] bytesInUtf16 = utf16StrToDecode.getBytes(UTF_8);
//            decoded = new String(bytesInUtf16, UTF_16);
//        } catch (UnsupportedEncodingException e) {
//            //TODO: add proper logging
//            System.out.println(wikiPageContent + " could not be parsed." + e.getMessage());
//        } finally {
//            return decoded;
//        }
//    }

}
