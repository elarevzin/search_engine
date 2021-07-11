package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.WikiPageQuery;

public interface QueryBuilder {

    WikiPageQuery buildQuery(String queryString);
}
