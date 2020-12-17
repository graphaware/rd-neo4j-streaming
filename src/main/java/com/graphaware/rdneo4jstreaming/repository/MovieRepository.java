package com.graphaware.rdneo4jstreaming.repository;

import com.graphaware.rdneo4jstreaming.domain.Movie;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface MovieRepository extends ReactiveNeo4jRepository<Movie, String> {

}
