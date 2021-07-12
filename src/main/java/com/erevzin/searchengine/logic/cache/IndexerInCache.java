package com.erevzin.searchengine.logic.cache;

public interface IndexerInCache {

    void indexTerm(String token, String wikiPageId);

    void indexWikiPage(String wikiPageId, String wikiPageContent);
}
