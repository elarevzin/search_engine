package com.erevzin.searchengine.persistance;

import com.erevzin.searchengine.api.WikiPageDTO;
import com.erevzin.searchengine.logic.TermIndexer;
import com.erevzin.searchengine.logic.WikiPageParser;
import com.erevzin.searchengine.model.WikiPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class DbSeeder {
	
	private final WikiPageCrudRepository wikiPageCrudRepository;
	private final WikiPageParser wikiPageParser;
	private final TermIndexer termIndexer;

	@Autowired
	public DbSeeder(WikiPageCrudRepository wikiPageCrudRepository , WikiPageParser wikiPageParser, TermIndexer termIndexer) {
		this.wikiPageCrudRepository = wikiPageCrudRepository;
		this.wikiPageParser = wikiPageParser;
		this.termIndexer = termIndexer;
	}

	public void populateWikiPagesDb() {
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
					wikiPageCrudRepository.save(new WikiPage(wikiPage.getWikiPageId(), wikiPage.getContent()));
					tokens.stream().forEach(token -> termIndexer.indexTerm(token, wikiPage.getWikiPageId()));
					System.out.println(wikiPage.getWikiPageId() + " --- " + wikiPage.getContent());

				});

	}

}
