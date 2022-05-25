package net.datafaker;

import net.datafaker.internal.helper.WordUtils;

/**
 * @since 0.8.0
 */
public class Animal {
    private final Faker faker;

    protected Animal(Faker faker) {
        this.faker = faker;
    }

    public String name() {
        return faker.fakeValuesService().resolve("creature.animal.name", this, faker);
    }

    public String scientificName() {
        return genus() + " " + species();
    }

    public String genus() {
        return WordUtils.capitalize(faker.fakeValuesService().resolve("creature.animal.genus", this, faker));
    }

    public String species() {
        return faker.fakeValuesService().resolve("creature.animal.species", this, faker).toLowerCase();
    }
}
