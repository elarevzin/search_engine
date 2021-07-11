package com.erevzin.searchengine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document
public class Term {

    @Id
    @Indexed
    private String term;
    private Set<String> wikiPages;

    public Term() {
    }

    public Term(String term, Set<String> wikiPages) {
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
