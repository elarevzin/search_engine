package com.erevzin.searchengine.logic.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndexerInCacheImpl implements IndexerInCache {

    private final WikiPageCacheAccessor wikiPageCacheAccessor;

    @Value("#{'${excludedTokens}'.split(',')}")
    private final List<String> excludedTokens;

    @Autowired
    public IndexerInCacheImpl(WikiPageCacheAccessor wikiPageCacheAccessor, List<String> excludedTokens) {
        this.wikiPageCacheAccessor = wikiPageCacheAccessor;
        this.excludedTokens = excludedTokens;
    }

    @Override
    public void indexTerm(String token, String wikiPageId) {
        if(excludedTokens.contains(token)){ //TODO: need to change to some more sophisticated mechanism, e.g. not to index by relative frequency
            return;
        }
        List<String> wikiPageIds = wikiPageCacheAccessor.getFromTermsCacheOrDefaultEmptyList(token);
        wikiPageIds.add(wikiPageId);
        wikiPageCacheAccessor.putInTermsCache(token, wikiPageIds);
    }

    @Override
    public void indexWikiPage(String wikiPageId, String wikiPageContent) {
        wikiPageCacheAccessor.putIfAbsentInWikiPageCache(wikiPageId, wikiPageContent);
    }
}
