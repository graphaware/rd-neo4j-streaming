package com.graphaware.rdneo4jstreaming.domain;

import lombok.Data;

@Data
public class Role {
	String name;
	Person actor;
	Movie movie;
}
