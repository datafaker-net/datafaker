package net.datafaker;

public class MassEffect extends AbstractProvider {
    protected MassEffect(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("mass-effect.characters");
    }

    public String species() {
        return faker.resolve("mass-effect.species");
    }

    public String cluster() {
        return faker.resolve("mass-effect.cluster");
    }

    public String planets() {
        return faker.resolve("mass-effect.planets");
    }

}
