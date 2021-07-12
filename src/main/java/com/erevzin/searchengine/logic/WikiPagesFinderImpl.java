package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.Term;
import com.erevzin.searchengine.model.WikiPage;
import com.erevzin.searchengine.model.WikiPageQuery;
import com.erevzin.searchengine.persistance.TermCrudRepository;
import com.erevzin.searchengine.persistance.WikiPageCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class WikiPagesFinderImpl implements WikiPagesFinder {

    private final TermCrudRepository termCrudRepository;
    private final WikiPageCrudRepository wikiPageCrudRepository;
    private final QueryBuilder queryBuilder;

    @Autowired
    public WikiPagesFinderImpl(TermCrudRepository termCrudRepository, WikiPageCrudRepository wikiPageCrudRepository, QueryBuilder queryBuilder) {
        this.termCrudRepository = termCrudRepository;
        this.wikiPageCrudRepository = wikiPageCrudRepository;
        this.queryBuilder = queryBuilder;
    }

    public List<WikiPage> findWikiPages(String queryString){
        List<WikiPage> wikiPagesFound = new ArrayList<>();
        WikiPageQuery query = queryBuilder.buildQuery(queryString);

        List<String> queryTerms = query.getQueryTerms();
        List<String> wikiPagesIdsFound = new ArrayList<>();

        for(String queryTerm : queryTerms) {
            Optional<Term> termFound = termCrudRepository.findById(queryTerm);
            termFound.ifPresent(term -> getWikiPagesIds(wikiPagesIdsFound, term.getWikiPages(), query.getQueryType()));
        }
        wikiPageCrudRepository.findAllById(wikiPagesIdsFound).forEach(wikiPagesFound::add);
        return wikiPagesFound;
    }

    private void getWikiPagesIds(List<String> wikiPagesIdsFound, Set<String> wikiPages, QueryType queryType) {
        List<String> idsFound = new ArrayList<>(wikiPages);
        if(queryType.equals(QueryType.AND) && !wikiPagesIdsFound.isEmpty()) {
            wikiPagesIdsFound.retainAll(idsFound);
        } else {
            wikiPagesIdsFound.addAll(idsFound);
        }

    }
}
