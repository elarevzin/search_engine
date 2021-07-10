package com.erevzin.searchengine.persistance;

import com.erevzin.searchengine.model.Term;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TermCrudRepository extends ReactiveMongoRepository<Term, String> {
}
