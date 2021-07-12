package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.api.WikiPageDTO;

import java.util.List;

public interface WikiPageParser {

    WikiPageDTO parseLine(String line);

    List<String> parseTokens(String wikiPageContent);

}
