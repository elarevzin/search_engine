package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.logic.cache.WikiPageCacheAccessor;
import com.erevzin.searchengine.logic.parsers.QueryBuilder;
import com.erevzin.searchengine.model.QueryType;
import com.erevzin.searchengine.model.WikiPageDTO;
import com.erevzin.searchengine.model.WikiPageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WikiPagesFinderImpl implements WikiPagesFinder {

    private final QueryBuilder queryBuilder;
    private final WikiPageCacheAccessor wikiPageCacheAccessor;
//    private final Cache<String, String> wikiPagesCache;
//    private final Cache<String, List<String>> termsCache;


    @Autowired
    public WikiPagesFinderImpl(QueryBuilder queryBuilder, WikiPageCacheAccessor wikiPageCacheAccessor) {
        this.queryBuilder = queryBuilder;
        this.wikiPageCacheAccessor = wikiPageCacheAccessor;
//        wikiPagesCache = wikiPageCacheAccessor.getWikiPagesCache();
//        termsCache = wikiPageCacheAccessor.getTermsCache();
    }

    public List<WikiPageDTO> findWikiPages(String queryString){
        WikiPageQuery query = queryBuilder.buildQuery(queryString);
        Set<String> wikiPagesIdsFound = handleFirstTerm(query.getQueryTerms());
        wikiPagesIdsFound = buildWikiPageIdsList(query, query.getQueryTerms(), wikiPagesIdsFound);

        return getWikiPagesFromCache(wikiPagesIdsFound);
    }

    private List<WikiPageDTO> getWikiPagesFromCache(Set<String> wikiPagesIdsFound) {
        List<WikiPageDTO> wikiPagesFound = new ArrayList<>();
        wikiPagesIdsFound.stream().forEach(wikiPageId -> {
            String wikiPageContent = wikiPageCacheAccessor.getFromWikiPagesCache(wikiPageId);
            wikiPagesFound.add(new WikiPageDTO(wikiPageId, wikiPageContent));
        });
        return wikiPagesFound;
    }

    private Set<String> handleFirstTerm(List<String> queryTerms ) {
        Set<String> wikiPagesIdsFound = new HashSet<>();
        if(!CollectionUtils.isEmpty(queryTerms)) {
            wikiPagesIdsFound.addAll(wikiPageCacheAccessor.getFromTermsCache(queryTerms.get(0)));
        }
        return wikiPagesIdsFound;
    }

    private Set<String> buildWikiPageIdsList(WikiPageQuery query, List<String> queryTerms, Set<String> wikiPagesIdsFound) {
        for(int i = 1; i <= queryTerms.size() -1 ; i++) {
            List<String> wikiPagesIdsPerTerms = wikiPageCacheAccessor.getFromTermsCache(queryTerms.get(i));
            wikiPagesIdsFound = getWikiPagesIds(wikiPagesIdsFound, wikiPagesIdsPerTerms, query.getQueryType());
        }
        return wikiPagesIdsFound;
    }

    private Set<String> getWikiPagesIds(Set<String> wikiPages, List<String> wikiPagesPerTerm, QueryType queryType) {
        if(queryType.equals(QueryType.AND)) {
            wikiPages.retainAll(wikiPagesPerTerm);
        } else {
            wikiPages.addAll(wikiPagesPerTerm);
        }
        return wikiPages;
    }
}
