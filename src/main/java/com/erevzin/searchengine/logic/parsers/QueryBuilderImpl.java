package com.erevzin.searchengine.logic.parsers;

import com.erevzin.searchengine.logic.exceptions.InvalidWikiPageQueryException;
import com.erevzin.searchengine.model.QueryType;
import com.erevzin.searchengine.model.WikiPageQuery;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.erevzin.searchengine.utilities.Patterns.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
public class QueryBuilderImpl implements QueryBuilder {

    @Override
    public WikiPageQuery buildQuery(String queryString) {
        WikiPageQuery wikiPageQuery = new WikiPageQuery();

        boolean andFound = andQueryPattern.matcher(queryString).find();
        boolean orFound = orQueryPattern.matcher(queryString).find();

        queryString = handleQueryByType(queryString, wikiPageQuery, andFound, orFound);

        if(andFound && orFound){
            throw new InvalidWikiPageQueryException("Not a valid query. Either AND or OR allowed");
        }
        wikiPageQuery.setQueryTerms(Arrays.asList(spacesPattern.split(queryString))
                .stream().map(String::toLowerCase).collect(Collectors.toList()));
        return wikiPageQuery;
    }

    private String handleQueryByType(String queryString, WikiPageQuery wikiPageQuery, boolean andFound, boolean orFound) {
        if(andFound){
            wikiPageQuery.setQueryType(QueryType.AND);
            queryString = queryString.replace(andQueryPattern.toString(), EMPTY);
        } else if(orFound) {
            wikiPageQuery.setQueryType(QueryType.OR);
            queryString = queryString.replace(orQueryPattern.toString(), EMPTY);
        } else {
            wikiPageQuery.setQueryType(QueryType.NONE);
        }
        return queryString;
    }
}
