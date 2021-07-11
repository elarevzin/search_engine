package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.WikiPage;

import java.util.List;

public interface WikiPageParser {

    WikiPage parseLine(String line);

    List<String> parseTokens(String wikiPageContent);

}
