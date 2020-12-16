package com.graphaware.rdneo4jstreaming.web;

import java.time.Duration;
import java.time.Instant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
public class SimpleReactiveController {

	@GetMapping("time")
	Flux<String> time() {
		return Flux
				.interval(Duration.ofSeconds(1))
				.limitRequest(100)
				.map(it -> Instant.now().toString());
	}
}
