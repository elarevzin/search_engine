package com.erevzin.searchengine.logic.parsers;

import com.erevzin.searchengine.model.WikiPageDTO;

import java.util.List;

public interface WikiPageParser {

    WikiPageDTO parseLine(String line);

    List<String> parseTokens(String wikiPageContent);

}
