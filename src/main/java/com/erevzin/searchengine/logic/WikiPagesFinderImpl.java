package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.api.WikiPageDTO;
import com.erevzin.searchengine.logic.cache.WikiPageCacheProvider;
import com.erevzin.searchengine.model.WikiPageQuery;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WikiPagesFinderImpl implements WikiPagesFinder {

    private final QueryBuilder queryBuilder;
    private final WikiPageCacheProvider wikiPageCacheProvider;
    private final Cache<String, String> wikiPagesCache;
    private final Cache<String, List<String>> termsCache;


    @Autowired
    public WikiPagesFinderImpl(QueryBuilder queryBuilder, WikiPageCacheProvider wikiPageCacheProvider) {
        this.queryBuilder = queryBuilder;
        this.wikiPageCacheProvider = wikiPageCacheProvider;
        wikiPagesCache = wikiPageCacheProvider.getWikiPagesCache();
        termsCache = wikiPageCacheProvider.getTermsCache();
    }

    public List<WikiPageDTO> findWikiPages(String queryString){
        List<WikiPageDTO> wikiPagesFound = new ArrayList<>();
        WikiPageQuery query = queryBuilder.buildQuery(queryString);

        List<String> queryTerms = query.getQueryTerms();
        Set<String> wikiPagesIdsFound = new HashSet<>();

        for(String queryTerm : queryTerms) {
            List<String> wikiPagesIdsPerTerms = termsCache.getUnchecked(queryTerm);
            wikiPagesIdsFound = getWikiPagesIds(wikiPagesIdsFound, wikiPagesIdsPerTerms, query.getQueryType());
        }

        wikiPagesIdsFound.stream().forEach(wikiPageId -> {
            String wikiPageContent = wikiPagesCache.getUnchecked(wikiPageId);
            wikiPagesFound.add(new WikiPageDTO(wikiPageId, wikiPageContent));
        });
        return wikiPagesFound;
    }

    private Set<String> getWikiPagesIds(Set<String> wikiPages, List<String> wikiPagesPerTerm, QueryType queryType) {
        if(queryType.equals(QueryType.AND) && !wikiPages.isEmpty()) {
            wikiPages.retainAll(wikiPagesPerTerm);
        } else {
            wikiPages.addAll(wikiPagesPerTerm);
        }
        return wikiPages;
    }
}
