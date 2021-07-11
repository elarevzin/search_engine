package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.WikiPage;

import java.util.List;

public interface WikiPagesFinder {

    List<WikiPage> findWikiPages(String query);

}
