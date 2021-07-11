package com.erevzin.searchengine.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wiki_pages")
public class WikiPage {

    @Id
    @Indexed
    private String wikiPageId;
    private String content;

    public WikiPage() {
    }

    public WikiPage(String wikiPageId, String content) {
        this.wikiPageId = wikiPageId;
        this.content = content;
    }

    public String getWikiPageId() {
        return wikiPageId;
    }

    public void setWikiPageId(String wikiPageId) {
        this.wikiPageId = wikiPageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

