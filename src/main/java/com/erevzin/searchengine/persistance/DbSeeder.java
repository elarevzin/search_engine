package com.erevzin.searchengine.persistance;

import com.erevzin.searchengine.logic.WikiPageIndexer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class DbSeeder {
	
	private final WikiPageCrudRepository wikiPageCrudRepository;
	private final TermCrudRepository termCrudRepository;
	private final WikiPageIndexer wikiPageIndexer;

	@Autowired
	public DbSeeder(WikiPageCrudRepository wikiPageCrudRepository, TermCrudRepository termCrudRepository, WikiPageIndexer wikiPageIndexer) {
		this.wikiPageCrudRepository = wikiPageCrudRepository;
		this.termCrudRepository = termCrudRepository;
		this.wikiPageIndexer = wikiPageIndexer;
	}

	@PostConstruct
	public void populateWikiPagesDb() throws Exception {
		// input file
		Path ipPath = Paths.get("wiki_data.txt");

		Flux<String> linesFlux = Flux.using(
				() -> Files.lines(ipPath),
				Flux::fromStream,
				Stream::close
		);

		linesFlux
				.subscribe(i -> {
					wikiPageCrudRepository.save(wikiPageIndexer.index(i+""));
					System.out.println("Observer-1 : " + i);
				});

	}

}
