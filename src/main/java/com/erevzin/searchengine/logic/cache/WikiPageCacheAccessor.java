package com.erevzin.searchengine.logic.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
public class WikiPageCacheAccessor {

    private final LoadingCache<String, String> wikiPagesCache;

    private final LoadingCache<String, List<String>> termsCache;

    public WikiPageCacheAccessor() {
        this.termsCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<>() {
                    @Override
                    public List<String> load(String key) {
                        return new ArrayList<>();
                    }
                });
        this.wikiPagesCache = CacheBuilder.newBuilder()
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String key) {
                        return key;
                    }
                });
    }

    public String getFromWikiPagesCache(String wikiPageId) {
        String wikiPageContentIfExist = wikiPagesCache.getIfPresent(wikiPageId);
        return wikiPageContentIfExist == null ? EMPTY : wikiPageContentIfExist;
    }

    public String putIfAbsentInWikiPageCache(String wikiPageId, String wikiPageContent){
        return wikiPagesCache.asMap().putIfAbsent(wikiPageId, wikiPageContent);
    }

    public List<String> getFromTermsCache(String term){
        List<String> wikiPagesIfExist = termsCache.getIfPresent(term);
        return wikiPagesIfExist == null ? new ArrayList<>() : wikiPagesIfExist;
    }

    public List<String> getFromTermsCacheOrDefaultEmptyList(String token){
        return termsCache.asMap().getOrDefault(token, new ArrayList<>());
    }

    public List<String> putInTermsCache(String token, List<String> wikiPageIds){
        return termsCache.asMap().put(token, wikiPageIds);
    }
}
