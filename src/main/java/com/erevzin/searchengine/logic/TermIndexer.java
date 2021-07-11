package com.erevzin.searchengine.logic;

public interface TermIndexer {

    void indexTerm(String token, String wikiPageId);
}
