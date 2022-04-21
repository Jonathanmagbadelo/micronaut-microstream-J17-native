package com.magbadelo.dto;

import io.micronaut.core.annotation.Introspected;

@Introspected
public record Book(String id, String name, int pageCount, Author author) {
}
