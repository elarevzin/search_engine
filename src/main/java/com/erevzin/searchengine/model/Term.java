package com.erevzin.searchengine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Term {

    @Id
    private String term;
    private List<String> wikiPages;

    public Term() {
    }

    public Term(String term, List<String> wikiPages) {
        this.term = term;
        this.wikiPages = wikiPages;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public List<String> getWikiPages() {
        return wikiPages;
    }

    public void setWikiPages(List<String> wikiPages) {
        this.wikiPages = wikiPages;
    }
}
