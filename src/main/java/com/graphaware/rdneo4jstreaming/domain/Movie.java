package com.graphaware.rdneo4jstreaming.domain;

import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
public class Movie {
	Integer released;
	String tagline;
	@Id
	String title;
	String description;
	LocalDateTime createdAt;
}
