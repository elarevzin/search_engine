package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.WikiPageQuery;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.regex.Matcher;

import static com.erevzin.searchengine.utilities.Patterns.*;
import static org.apache.logging.log4j.util.Strings.EMPTY;

@Service
public class QueryBuilderImpl implements QueryBuilder {

    @Override
    public WikiPageQuery buildQuery(String queryString) {
        WikiPageQuery wikiPageQuery = new WikiPageQuery();
        validate(queryString);
        Matcher andQueryMatcher = andQueryPattern.matcher(queryString);
        Matcher orQueryMatcher = orQueryPattern.matcher(queryString);
        if(andQueryMatcher.find()){
            wikiPageQuery.setQueryType(QueryType.AND);
            queryString = queryString.replace(andQueryPattern.toString(), EMPTY);
        } else if(orQueryMatcher.find()) {
            wikiPageQuery.setQueryType(QueryType.OR);
            queryString = queryString.replace(orQueryPattern.toString(), EMPTY);
        } else {
            wikiPageQuery.setQueryType(QueryType.NONE);

        }

        wikiPageQuery.setQueryTerms(Arrays.asList(spacesPattern.split(queryString)));
        return wikiPageQuery;
    }

    private void validate(String queryString) {
        //TODO: add query String validations
    }
}
