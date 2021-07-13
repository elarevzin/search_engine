package com.erevzin.searchengine.logic.cache;

import com.erevzin.searchengine.model.WikiPageDTO;
import com.erevzin.searchengine.logic.parsers.WikiPageParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class WikiPageCacheLoader {
	
	private final WikiPageParser wikiPageParser;
	private final IndexerInCache indexerInCache;

	@Autowired
	public WikiPageCacheLoader(WikiPageParser wikiPageParser, IndexerInCache indexerInCache) {
		this.wikiPageParser = wikiPageParser;
		this.indexerInCache = indexerInCache;
	}

	@PostConstruct
	public void populateWikiPagesCache() {
		// input file
		Path ipPath = Paths.get("wiki_data.txt");

		Flux<String> linesFlux = Flux.using(
				() -> Files.lines(ipPath),
				Flux::fromStream,
				Stream::close
		);

		linesFlux
				.subscribe(i -> {
					WikiPageDTO wikiPage = wikiPageParser.parseLine(i+"");
					List<String> tokens = wikiPageParser.parseTokens(wikiPage.getContent());
					indexerInCache.indexWikiPage(wikiPage.getWikiPageId(), wikiPage.getContent());
					tokens.stream().forEach(token -> indexerInCache.indexTerm(token, wikiPage.getWikiPageId()));
					System.out.println(wikiPage.getWikiPageId() + " --- " + wikiPage.getContent());

				});

	}

}
