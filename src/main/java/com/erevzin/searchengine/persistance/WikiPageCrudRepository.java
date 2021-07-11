package com.erevzin.searchengine.persistance;

import com.erevzin.searchengine.model.WikiPage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WikiPageCrudRepository extends MongoRepository<WikiPage, String> {

}
