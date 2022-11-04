package com.free.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
	USER("ROLE_USER"),
	SOCIAL("ROLE_SOCIAL");
	
	private final String value;

	
	
}
