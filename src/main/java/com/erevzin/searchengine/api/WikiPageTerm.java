package com.erevzin.searchengine.api;

import java.util.Set;

public class WikiPageTerm {

    private String term;
    private Set<String> wikiPages;

    public WikiPageTerm() {
    }

    public WikiPageTerm(String term, Set<String> wikiPages) {
        this.term = term;
        this.wikiPages = wikiPages;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Set<String> getWikiPages() {
        return wikiPages;
    }

    public void setWikiPages(Set<String> wikiPages) {
        this.wikiPages = wikiPages;
    }
}
