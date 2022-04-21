package com.magbadelo.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record Author(String id, String firstName, String lastName) {
}
