package com.erevzin.searchengine.model;

import com.erevzin.searchengine.logic.QueryType;

import java.util.List;

public class WikiPageQuery {

    private QueryType queryType;
    private List<String> queryTerms;

    public WikiPageQuery() {
    }

    public WikiPageQuery(QueryType queryType, List<String> queryTerms) {
        this.queryType = queryType;
        this.queryTerms = queryTerms;
    }

    public QueryType getQueryType() {
        return queryType;
    }

    public void setQueryType(QueryType queryType) {
        this.queryType = queryType;
    }

    public List<String> getQueryTerms() {
        return queryTerms;
    }

    public void setQueryTerms(List<String> queryTerms) {
        this.queryTerms = queryTerms;
    }
}
