package com.graphaware.rdneo4jstreaming.web;

import java.time.Duration;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class SimpleReactiveControllerTest {

	private final SimpleReactiveController controller = new SimpleReactiveController(null);

	@Test
	public void should_return_100_times_time() {
		StepVerifier.withVirtualTime(controller::time)
				.thenAwait(Duration.ofSeconds(100))
				.expectNextCount(100)
				.verifyComplete();
	}
}