package com.erevzin.searchengine.api;

public class WikiPageDTO {

    private String wikiPageId;
    private String content;

    public WikiPageDTO() {
    }

    public WikiPageDTO(String wikiPageId, String content) {
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

