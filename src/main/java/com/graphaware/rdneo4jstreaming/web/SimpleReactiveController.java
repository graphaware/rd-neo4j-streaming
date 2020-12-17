package com.graphaware.rdneo4jstreaming.web;

import com.graphaware.rdneo4jstreaming.domain.Movie;
import com.graphaware.rdneo4jstreaming.repository.MovieRepository;
import java.time.Duration;
import java.time.Instant;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SimpleReactiveController {

	private final MovieRepository movieRepository;

	public SimpleReactiveController(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@GetMapping("time")
	Flux<String> time() {
		return Flux
				.interval(Duration.ofSeconds(1))
				.limitRequest(100)
				.map(it -> Instant.now().toString());
	}

	@GetMapping(value = "/movies", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	Flux<Movie> findAllMovies() {
		return movieRepository.findAll();
	}

	@MessageMapping("find.movies")
	public Flux<Movie> radars() {
		return movieRepository.findAll();
	}
}
