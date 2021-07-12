package com.erevzin.searchengine.logic.parsers;

import com.erevzin.searchengine.logic.exceptions.InvalidWikiPageQueryException;
import com.erevzin.searchengine.model.QueryType;
import com.erevzin.searchengine.model.WikiPageQuery;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static com.erevzin.searchengine.utilities.Patterns.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
public class QueryBuilderImpl implements QueryBuilder {

    @Override
    public WikiPageQuery buildQuery(String queryString) {
        WikiPageQuery wikiPageQuery = new WikiPageQuery();
        Matcher andQueryMatcher = andQueryPattern.matcher(queryString);
        Matcher orQueryMatcher = orQueryPattern.matcher(queryString);

        boolean andFound = andQueryMatcher.find();
        boolean orFound = orQueryMatcher.find();

        if(andFound){
            wikiPageQuery.setQueryType(QueryType.AND);
            queryString = queryString.replace(andQueryPattern.toString(), EMPTY);
        } else {
            if(orFound) {
                wikiPageQuery.setQueryType(QueryType.OR);
                queryString = queryString.replace(orQueryPattern.toString(), EMPTY);
            } else {
                wikiPageQuery.setQueryType(QueryType.NONE);
            }
        }

        if(andFound && orFound){
            throw new InvalidWikiPageQueryException("Not a valid query. Either AND or OR allowed");
        }
        wikiPageQuery.setQueryTerms(Arrays.asList(spacesPattern.split(queryString))
                .stream().map(String::toLowerCase).collect(Collectors.toList()));
        return wikiPageQuery;
    }

    private void validate(String queryString, Matcher andQueryMatcher, Matcher orQueryMatcher) {
        //TODO: add query String validations
    }
}
