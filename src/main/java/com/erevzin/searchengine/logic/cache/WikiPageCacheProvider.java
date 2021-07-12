package com.erevzin.searchengine.logic.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WikiPageCacheProvider {

    private Cache<String, String> wikiPagesCache = CacheBuilder.newBuilder()
            .concurrencyLevel(10)
            .build(new CacheLoader<>() {
                @Override
                public String load(String key) {
                    return key;
                }
            });

    private Cache<String, List<String>> termsCache = CacheBuilder.newBuilder()
            .concurrencyLevel(10)
            .build(new CacheLoader<>() {
                @Override
                public List<String> load(String key) {
                    return new ArrayList<>();
                }
            });

    public Cache<String, String> getWikiPagesCache() {
        return wikiPagesCache;
    }

    public Cache<String, List<String>> getTermsCache() {
        return termsCache;
    }
}
