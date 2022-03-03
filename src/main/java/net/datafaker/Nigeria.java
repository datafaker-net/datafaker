package net.datafaker.service;

import net.datafaker.Faker;

public class Nigeria {

    private final Faker faker;

    public Nigeria(Faker faker) {
        this.faker = faker;
    }

    public String places() {
        return faker.resolve("nigeria.places");
    }

    public String name() {
        return  faker.resolve("nigeria.name");
    }

    public String food() {
        return faker.resolve("nigeria.food");
    }

    public String schools() {
        return faker.resolve("nigeria.schools");
    }

    public String celebrities(){
        return faker.resolve("nigeria.celebrities");
    }
}



