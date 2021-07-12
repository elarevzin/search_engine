package com.erevzin.searchengine.logic;

import com.erevzin.searchengine.api.WikiPageDTO;

import java.util.List;

public interface WikiPagesFinder {

    List<WikiPageDTO> findWikiPages(String query);

}
