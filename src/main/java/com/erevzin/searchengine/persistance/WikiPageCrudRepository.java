package com.erevzin.searchengine.persistance;

import com.erevzin.searchengine.model.WikiPage;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface WikiPageCrudRepository extends ReactiveMongoRepository<WikiPage, String> {

}
