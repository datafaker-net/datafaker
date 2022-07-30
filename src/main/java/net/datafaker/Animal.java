package net.datafaker;

import net.datafaker.internal.helper.WordUtils;

/**
 * @since 0.8.0
 */
public class Animal extends AbstractProvider {

    protected Animal(Faker faker) {
        super(faker);
    }

    public String name() {
        return faker.fakeValuesService().resolve("creature.animal.name", this);
    }

    public String scientificName() {
        return genus() + " " + species();
    }

    public String genus() {
        return WordUtils.capitalize(faker.fakeValuesService().resolve("creature.animal.genus", this));
    }

    public String species() {
        return faker.fakeValuesService().resolve("creature.animal.species", this).toLowerCase();
    }
}
