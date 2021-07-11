package com.erevzin.searchengine.persistance;

import com.erevzin.searchengine.model.Term;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TermCrudRepository extends MongoRepository<Term, String> {
}
