package com.erevzin.searchengine.logic.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WikiPageCacheAccessor {

    private final Cache<String, String> wikiPagesCache;

    private final Cache<String, List<String>> termsCache;

    public WikiPageCacheAccessor() {
        this.termsCache = CacheBuilder.newBuilder()
                .concurrencyLevel(10)
                .build(new CacheLoader<>() {
                    @Override
                    public List<String> load(String key) {
                        return new ArrayList<>();
                    }
                });
        this.wikiPagesCache = CacheBuilder.newBuilder()
                .concurrencyLevel(10)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String key) {
                        return key;
                    }
                });
    }

    public String getFromWikiPagesCache(String wikiPageId) {
        return wikiPagesCache.getUnchecked(wikiPageId);
    }

    public String putIfAbsentInCache(String wikiPageId, String wikiPageContent){
        return wikiPagesCache.asMap().putIfAbsent(wikiPageId, wikiPageContent);
    }

    public List<String> getFromTermsCache(String term){
        return termsCache.getUnchecked(term);
    }

    public List<String> getFromTermsCacheOrDefaultEmptyList(String token){
        return termsCache.asMap().getOrDefault(token, new ArrayList<>());
    }

    public List<String> putInTermsCache(String token, List<String> wikiPageIds){
        return termsCache.asMap().put(token, wikiPageIds);
    }
}
