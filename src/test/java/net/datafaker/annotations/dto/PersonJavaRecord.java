package net.datafaker.annotations.dto;

import net.datafaker.annotations.FakeForSchema;

@FakeForSchema("net.datafaker.annotations.FakeAnnotationTest#defaultSchema")
public record PersonJavaRecord(String name) {
}
