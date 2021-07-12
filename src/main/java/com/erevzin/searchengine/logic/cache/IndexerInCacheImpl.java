package com.erevzin.searchengine.logic.cache;

import com.erevzin.searchengine.logic.TokenParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class IndexerInCacheImpl implements IndexerInCache {

    private final TokenParser tokenParser;
    private final WikiPageCacheProvider wikiPageCacheProvider;

    @Value("#{'${excludedTokens}'.split(',')}")
    private List<String> excludedTokens;

    @Autowired
    public IndexerInCacheImpl(TokenParser tokenParser, WikiPageCacheProvider wikiPageCacheProvider) {
        this.tokenParser = tokenParser;
        this.wikiPageCacheProvider = wikiPageCacheProvider;
    }

    @Override
    public void indexTerm(String token, String wikiPageId) {
        if(excludedTokens.contains(token)){ //TODO: need to change to some more sophisticated mechanism, e.g. not to index by relative frequency
            return;
        }
        String parsedToken = tokenParser.parseToken(token);
        List<String> wikiPageIds = wikiPageCacheProvider.getTermsCache().asMap().getOrDefault(parsedToken, new ArrayList<>());
        wikiPageIds.add(wikiPageId);
        wikiPageCacheProvider.getTermsCache().asMap().put(parsedToken, wikiPageIds);
    }

    @Override
    public void indexWikiPage(String wikiPageId, String wikiPageContent) {
        wikiPageCacheProvider.getWikiPagesCache().asMap().putIfAbsent(wikiPageId, wikiPageContent);
    }
}
