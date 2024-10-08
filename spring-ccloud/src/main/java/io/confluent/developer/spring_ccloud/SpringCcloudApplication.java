package io.confluent.developer.spring_ccloud;

import java.time.Duration;
import java.util.stream.Stream;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringCcloudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCcloudApplication.class, args);
	}
}

@RequiredArgsConstructor
@Component
class Producer {
	private final KafkaTemplate<Integer, String> template;
	private final Faker faker = Faker.instance(); // Initialize Faker here

	@EventListener(ApplicationStartedEvent.class)
	public void generate() {
		final Flux<Long> interval = Flux.interval(Duration.ofMillis(1_000));
		final Flux<String> quotes = Flux.fromStream(Stream.generate(() -> faker.hobbit().quote()));

		// Note: Using `flatMap` instead of `map` to handle asynchronous operations
		Flux.zip(interval, quotes)
			.flatMap(it -> template.send("hobbit", faker.random().nextInt(42), it.getT2()).completable())
			.doOnError(error -> System.err.println("Error sending message to Kafka: " + error.getMessage()))
			.subscribe(); // Subscribe to start the Flux processing
	}
}
