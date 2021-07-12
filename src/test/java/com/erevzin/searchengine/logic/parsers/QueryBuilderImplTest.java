package com.erevzin.searchengine.logic.parsers;

import com.erevzin.searchengine.logic.exceptions.InvalidWikiPageQueryException;
import com.erevzin.searchengine.model.QueryType;
import com.erevzin.searchengine.model.WikiPageQuery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.erevzin.searchengine.logic.parsers.QueryBuilderImpl.INVALID_QUERY;
import static org.junit.jupiter.api.Assertions.*;


class QueryBuilderImplTest {

    private QueryBuilder queryBuilder;

    @BeforeEach
    public void init(){
        queryBuilder = new QueryBuilderImpl();
    }

    @Test
    void buildAndQueryTest() {

        WikiPageQuery actual = queryBuilder.buildQuery("cat AND dog");
        assertEquals(QueryType.AND, actual.getQueryType());
        assertTrue(actual.getQueryTerms().contains("cat"));
        assertTrue(actual.getQueryTerms().contains("dog"));

    }

    @Test
    void buildOrQueryTest() {

        WikiPageQuery actual = queryBuilder.buildQuery("cat OR dog");
        assertEquals(QueryType.OR, actual.getQueryType());
        assertTrue(actual.getQueryTerms().contains("cat"));
        assertTrue(actual.getQueryTerms().contains("dog"));

    }

    @Test
    void buildOneTermQueryTest() {

        WikiPageQuery actual = queryBuilder.buildQuery("cat");
        assertEquals(QueryType.NONE, actual.getQueryType());
        assertTrue(actual.getQueryTerms().contains("cat"));

    }

    @Test
    void invalidQueryTest() {
        Exception exception = assertThrows(InvalidWikiPageQueryException.class, () -> {
            queryBuilder.buildQuery("cat OR dog AND snake");
        });
        assertEquals(INVALID_QUERY, exception.getMessage());
    }
}