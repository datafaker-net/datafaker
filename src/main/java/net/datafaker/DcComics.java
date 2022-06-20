package net.datafaker;

import net.datafaker.core.Faker;

public class DcComics {

    private final Faker faker;

    public DcComics(Faker faker) {
        this.faker = faker;
    }

    public String hero() {
        return faker.resolve("dc_comics.hero");
    }

    public String heroine() {
        return faker.resolve("dc_comics.heroine");
    }

    public String villain() {
        return faker.resolve("dc_comics.villain");
    }

    public String name() {
        return faker.resolve("dc_comics.name");
    }

    public String title() {
        return faker.resolve("dc_comics.title");
    }
}
