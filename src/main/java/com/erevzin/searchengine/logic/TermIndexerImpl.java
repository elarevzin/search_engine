package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.Term;
import com.erevzin.searchengine.persistance.TermCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TermIndexerImpl implements  TermIndexer{

    private final TokenParser tokenParser;
    private final TermCrudRepository termCrudRepository;

    @Value("#{'${excludedTokens}'.split(',')}")
    private List<String> excludedTokens;

    @Autowired
    public TermIndexerImpl(TokenParser tokenParser, TermCrudRepository termCrudRepository) {
        this.tokenParser = tokenParser;
        this.termCrudRepository = termCrudRepository;
    }

    @Override
    public void indexTerm(String token, String wikiPageId) {
        String parsedToken = tokenParser.parseToken(token);
        if(excludedTokens.contains(parsedToken)){
            return;
        }

        Optional<Term> foundTerm = termCrudRepository.findById(parsedToken);
        if(foundTerm.isPresent()){
            foundTerm.get().getWikiPages().add(wikiPageId);
            termCrudRepository.save(foundTerm.get());
        }
        else {
            Set<String> wikiPages = new HashSet<>();
            wikiPages.add(wikiPageId);
            Term termToSave = new Term(parsedToken, wikiPages);
            System.out.println(parsedToken + " --- " + wikiPages.toString());
            termCrudRepository.save(termToSave);
        }
    }
}
