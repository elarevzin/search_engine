package com.erevzin.searchengine.logic.parsers;

import com.erevzin.searchengine.model.WikiPageQuery;

public interface QueryBuilder {

    WikiPageQuery buildQuery(String queryString);
}
