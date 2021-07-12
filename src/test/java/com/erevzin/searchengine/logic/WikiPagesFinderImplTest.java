package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.logic.cache.WikiPageCacheAccessor;
import com.erevzin.searchengine.logic.parsers.QueryBuilder;
import com.erevzin.searchengine.logic.parsers.QueryBuilderImpl;
import com.erevzin.searchengine.model.QueryType;
import com.erevzin.searchengine.model.WikiPageDTO;
import com.erevzin.searchengine.model.WikiPageQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WikiPagesFinderImplTest {

    private WikiPagesFinder wikiPagesFinder;
    @Mock
    private QueryBuilder queryBuilder;
    @Mock
    private WikiPageCacheAccessor wikiPageCacheAccessor;

    @BeforeEach
    public void init(){
        this.queryBuilder = mock(QueryBuilderImpl.class);
        this.wikiPageCacheAccessor = mock(WikiPageCacheAccessor.class);
        this.wikiPagesFinder = new WikiPagesFinderImpl(queryBuilder, wikiPageCacheAccessor);
    }

    @Test
    void findWikiPages_andQuery() {
        List<String> catWikiPages = new ArrayList<>();
        catWikiPages.add("1234");
        catWikiPages.add("999");

        List<String> dogWikiPages = new ArrayList<>();
        dogWikiPages.add("1234");
        when(wikiPageCacheAccessor.getFromTermsCache("cat")).thenReturn(catWikiPages);
        when(wikiPageCacheAccessor.getFromTermsCache("dog")).thenReturn(dogWikiPages);
        String queryString = "cat AND dog";
        List<String> queryTerms = new ArrayList<>();
        queryTerms.add("cat");
        queryTerms.add("dog");

        when(queryBuilder.buildQuery(queryString)).thenReturn(new WikiPageQuery(QueryType.AND, queryTerms));
        List<WikiPageDTO> found = wikiPagesFinder.findWikiPages(queryString);
        assertEquals(1, found.size());
        assertEquals("1234", found.get(0).getWikiPageId());

    }

    @Test
    void findWikiPages_andQuery_notFoud() {
        List<String> catWikiPages = new ArrayList<>();
        catWikiPages.add("555");
        catWikiPages.add("999");

        List<String> dogWikiPages = new ArrayList<>();
        dogWikiPages.add("1234");
        when(wikiPageCacheAccessor.getFromTermsCache("cat")).thenReturn(catWikiPages);
        when(wikiPageCacheAccessor.getFromTermsCache("dog")).thenReturn(dogWikiPages);
        String queryString = "cat AND dog";
        List<String> queryTerms = new ArrayList<>();
        queryTerms.add("cat");
        queryTerms.add("dog");

        when(queryBuilder.buildQuery(queryString)).thenReturn(new WikiPageQuery(QueryType.AND, queryTerms));
        List<WikiPageDTO> found = wikiPagesFinder.findWikiPages(queryString);
        assertTrue(found.isEmpty());

    }

    @Test
    void findWikiPages_orQuery() {
        List<String> catWikiPages = new ArrayList<>();
        catWikiPages.add("555");
        catWikiPages.add("999");

        List<String> dogWikiPages = new ArrayList<>();
        dogWikiPages.add("1234");
        when(wikiPageCacheAccessor.getFromTermsCache("cat")).thenReturn(catWikiPages);
        when(wikiPageCacheAccessor.getFromTermsCache("dog")).thenReturn(dogWikiPages);
        String queryString = "cat OR dog";
        List<String> queryTerms = new ArrayList<>();
        queryTerms.add("cat");
        queryTerms.add("dog");

        when(queryBuilder.buildQuery(queryString)).thenReturn(new WikiPageQuery(QueryType.OR, queryTerms));
        List<WikiPageDTO> found = wikiPagesFinder.findWikiPages(queryString);
        assertEquals(3, found.size());

    }

    @Test
    void findWikiPages_orQuery_notFound() {
        String queryString = "snake OR eagle";
        List<String> queryTerms = new ArrayList<>();
        queryTerms.add("snake");
        queryTerms.add("eagle");

        when(queryBuilder.buildQuery(queryString)).thenReturn(new WikiPageQuery(QueryType.OR, queryTerms));
        List<WikiPageDTO> found = wikiPagesFinder.findWikiPages(queryString);
        assertTrue(found.isEmpty());

    }

    @Test
    void findWikiPages_singleTerm() {
        List<String> catWikiPages = new ArrayList<>();
        catWikiPages.add("555");
        catWikiPages.add("999");
        when(wikiPageCacheAccessor.getFromTermsCache("cat")).thenReturn(catWikiPages);
        String queryString = "cat";
        List<String> queryTerms = new ArrayList<>();
        queryTerms.add("cat");

        when(queryBuilder.buildQuery(queryString)).thenReturn(new WikiPageQuery(QueryType.OR, queryTerms));
        List<WikiPageDTO> found = wikiPagesFinder.findWikiPages(queryString);
        assertEquals(2, found.size());

    }

    @Test
    void findWikiPages_singleTerm_notFound() {

        String queryString = "snake OR eagle";
        List<String> queryTerms = new ArrayList<>();
        queryTerms.add("snake");
        queryTerms.add("eagle");

        when(queryBuilder.buildQuery(queryString)).thenReturn(new WikiPageQuery(QueryType.OR, queryTerms));
        List<WikiPageDTO> found = wikiPagesFinder.findWikiPages(queryString);
        assertTrue(found.isEmpty());

    }
}