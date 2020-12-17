package com.graphaware.rdneo4jstreaming.repository;

import java.util.function.Consumer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.test.StepVerifier;


@DataNeo4jTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
class MovieRepositoryTest {

	@Autowired
	MovieRepository movieRepository;


	@Test
	void should_load_movies() {

		StepVerifier.create(movieRepository.findAll())
				.expectNextCount(38)
				.verifyComplete();
	}

//	@Test
	void hack_concat_fluxes() {


		Flux<Integer> f = Flux.just(1, 2, 3);
		Flux<Integer> f2 = Flux.just(1, 2, 3);
		// https://projectreactor.io/docs/core/release/reference/#producing.create
		Flux<Integer> f3 = Flux.create(new Consumer<FluxSink<Integer>>() {
			@Override
			public void accept(FluxSink<Integer> integerSynchronousSink) {

				f
						.doOnComplete(() -> {
							oneMore(integerSynchronousSink);
						})
						.subscribe(integerSynchronousSink::next);
			}

			void oneMore(FluxSink<Integer> integerSynchronousSink) {

				Flux.just(1, 2, 3).doOnComplete(() -> {
					integerSynchronousSink.complete();
				})
						.doOnComplete(() -> {
							oneMore(integerSynchronousSink);
						})
						.subscribe(integerSynchronousSink::next);
			}
		});

		f3
				.doOnNext(i -> System.out.println(i))
				.take(10)
				.as(StepVerifier::create)
				.expectNext(1,2,3,1,2,3,1,2,3,1)
				.verifyComplete();

	}
}