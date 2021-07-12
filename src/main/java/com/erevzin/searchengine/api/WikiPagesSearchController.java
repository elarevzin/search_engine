package com.erevzin.searchengine.api;

import com.erevzin.searchengine.logic.WikiPagesFinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/wiki-pages")
public class WikiPagesSearchController {

    private final WikiPagesFinder wikiPagesFinder;


    public WikiPagesSearchController(WikiPagesFinder wikiPagesFinder) {
        this.wikiPagesFinder = wikiPagesFinder;
    }

    @GetMapping("/{query}")
    private Flux<WikiPageDTO> getWikiPagesByQuery(@PathVariable String query) {
        return Flux.fromIterable(wikiPagesFinder.findWikiPages(query));
    }

}
