package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.persistance.TermCrudRepository;
import com.erevzin.searchengine.persistance.WikiPageCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class WikiPagesFinderImpl implements WikiPagesFinder {

    private final TermCrudRepository termCrudRepository;
    private final WikiPageCrudRepository wikiPageCrudRepository;

    @Autowired
    public WikiPagesFinderImpl(TermCrudRepository termCrudRepository, WikiPageCrudRepository wikiPageCrudRepository) {
        this.termCrudRepository = termCrudRepository;
        this.wikiPageCrudRepository = wikiPageCrudRepository;
    }

    public Flux<String> findWikiPages(String query){
        List<String> list = new ArrayList<>(Arrays.asList("first","second","third"));

        //TODO:
        //0. decide on query type: AND / OR
        //1. call Splitter Utility pass the Enum (AND/ OR) and the queryString receive back List<String>
        //2. call termCrudRepository find all the WikiPagesIds by the terms. returns Flux of List<String>
        //3. do either union (if AND) of take the intersection (if OR)
        //4. call wikiPageCrudRepository find all the WikiPagesIds by the ids. return found Flux of List<String>
        return Flux.using(
                () -> list,
                (l) -> Flux.fromIterable(l),
                (l) -> l.clear()
        );

    }
}
