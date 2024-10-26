package net.datafaker.annotations.dto;

import net.datafaker.annotations.FakeForSchema;

@FakeForSchema("net.datafaker.annotations.FakeAnnotationTest#defaultSchema")
public class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
