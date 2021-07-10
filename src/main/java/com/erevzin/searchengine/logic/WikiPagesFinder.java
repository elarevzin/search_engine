package com.erevzin.searchengine.logic;

import reactor.core.publisher.Flux;

public interface WikiPagesFinder {

    Flux<String> findWikiPages(String query);

}
