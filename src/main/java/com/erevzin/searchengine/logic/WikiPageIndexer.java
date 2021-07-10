package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.model.WikiPage;

public interface WikiPageIndexer {

    WikiPage index(String line);
}
