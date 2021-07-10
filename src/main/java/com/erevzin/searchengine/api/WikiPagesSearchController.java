package com.erevzin.searchengine.api;

import com.erevzin.searchengine.logic.WikiPagesFinder;
import com.erevzin.searchengine.model.WikiPage;
import com.erevzin.searchengine.persistance.WikiPageCrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/wiki-pages")
public class WikiPagesSearchController {

    private final WikiPagesFinder wikiPagesFinder;
    private final WikiPageCrudRepository wikiPageCrudRepository;


    public WikiPagesSearchController(WikiPagesFinder wikiPagesFinder, WikiPageCrudRepository wikiPageCrudRepository) {
        this.wikiPagesFinder = wikiPagesFinder;
        this.wikiPageCrudRepository = wikiPageCrudRepository;
    }

    @GetMapping("/{query}")
    private ArrayList<WikiPage> getWikiPagesByQuery(@PathVariable String query) {
        ArrayList<WikiPage> wikiPages = new ArrayList();
        wikiPageCrudRepository.findAll()
                .doOnNext(wikiPage -> addWikiPage(wikiPages, wikiPage))
                .subscribe();
        return wikiPages;
    }

    private boolean addWikiPage(ArrayList<WikiPage> wikiPages, WikiPage wikiPage) {
        return wikiPages.add(wikiPage);
    }
}
