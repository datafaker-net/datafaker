package de.bund.bka.hwa.Faker;

import net.datafaker.Faker;

public class StarWars {
    private final Faker faker;

    protected StarWars(Faker faker) {
        this.faker = faker;
    }

    public String character() {
        return faker.fakeValuesService().resolve("star_wars.characters", this, faker);
    }

    public String callSign() {
        return faker.fakeValuesService().resolve("star_wars.call_sign", this, faker);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("star_wars.quotes", this, faker);
    }

    public String vehicles() {
        return faker.fakeValuesService().resolve("star_wars.vehicles", this, faker);
    }

    public String droids() {
        return faker.fakeValuesService().resolve("star_wars.droids", this, faker);
    }

    public String planets() {
        return faker.fakeValuesService().resolve("star_wars.planets", this, faker);
    }

    public String species() {
        return faker.fakeValuesService().resolve("star_wars.species", this, faker);
    }

    public String wookieWords() {
        return faker.fakeValuesService().resolve("star_wars.wookiee_words", this, faker);
    }

    public String alternateCharacterSpelling() {
        return faker.fakeValuesService().resolve("star_wars.alternate_character_spellings", this, faker);
    }
}
