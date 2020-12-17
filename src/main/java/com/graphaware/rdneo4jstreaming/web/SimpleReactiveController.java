package com.graphaware.rdneo4jstreaming.web;

import com.graphaware.rdneo4jstreaming.domain.Movie;
import com.graphaware.rdneo4jstreaming.repository.MovieRepository;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	public Flux<Movie> findMovies() {

		return  findMoviesEndless(null);
	}

	// can be called using
	// rsocket-cli --route=find.movies --debug  ws://localhost:8080/rsocket

	// run CREATE (m:Movie {title:'my new movie', createdAt:localdatetime()})
	// in the browser
	public Flux<Movie> findMoviesEndless(LocalDateTime lastRequest) {

		Flux<Movie> movies = lastRequest == null ? movieRepository.findAll()
				: movieRepository.findAllByCreatedAtAfter(lastRequest);

		return movies
				.limitRate(10) // Limiting the prefetch rate prevents going into a stack overflow with the recursive fluxes
				.delayElements(Duration.ofMillis(100)) // This is not necessary ;)
				.concatWith(Flux.deferContextual(
						ctx -> findMoviesEndless(ctx.get("l"))).delaySubscription(Duration.ofSeconds(2))) // delete the inner subscription a bit
				.contextWrite(context -> context.put("l", LocalDateTime.now(ZoneId.of("UTC"))));
	}

}
