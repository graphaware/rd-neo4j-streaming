package com.graphaware.rdneo4jstreaming.repository;

import com.graphaware.rdneo4jstreaming.domain.Movie;
import java.time.LocalDateTime;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import reactor.core.publisher.Flux;

public interface MovieRepository extends ReactiveNeo4jRepository<Movie, String> {

	Flux<Movie> findAllByCreatedAtAfter(LocalDateTime someDate);

}
