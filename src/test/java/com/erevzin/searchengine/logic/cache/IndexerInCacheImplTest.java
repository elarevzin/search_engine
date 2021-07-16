package com.erevzin.searchengine.logic.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IndexerInCacheImplTest {

    private IndexerInCache indexerInCache;
    @Mock
    private WikiPageCacheAccessor wikiPageCacheAccessor;
    @Mock
    private List<String> excludedTokens = mock(List.class);



    @BeforeEach
    public void init(){
        wikiPageCacheAccessor = mock(WikiPageCacheAccessor.class);
        indexerInCache = new IndexerInCacheImpl(wikiPageCacheAccessor, excludedTokens);
    }



    @Test
    public void test_indexTerm() {
        String token = "lion";
        String wikiPageId = "44";
        List<String> wikiPageIds = new ArrayList<>();
        wikiPageIds.add(wikiPageId);
        when(wikiPageCacheAccessor.getFromTermsCacheOrDefaultEmptyList(any())).thenReturn(wikiPageIds);
        when(wikiPageCacheAccessor.putInTermsCache(any(), any())).thenReturn(wikiPageIds);
        indexerInCache.indexTerm(token, wikiPageId);
        verify(wikiPageCacheAccessor, times(1)).getFromTermsCacheOrDefaultEmptyList(token);
        verify(wikiPageCacheAccessor, times(1)).putInTermsCache(token, wikiPageIds);

    }

    @Test
    public void test_indexWikiPage() {
        String wikiPageContent = "Johnny Brown (born May 15, 1963)";
        String wikiPageId = "44";
        when(wikiPageCacheAccessor.putIfAbsentInWikiPageCache(any(), any())).thenReturn(wikiPageContent);
        indexerInCache.indexWikiPage(wikiPageId, wikiPageContent);
        verify(wikiPageCacheAccessor, times(1)).putIfAbsentInWikiPageCache(wikiPageId, wikiPageContent);
    }

}