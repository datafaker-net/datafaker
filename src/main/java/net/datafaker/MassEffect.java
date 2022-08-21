package net.datafaker;

public class MassEffect extends AbstractProvider {
    protected MassEffect(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("mass_effect.characters");
    }

    public String specie() {
        return faker.resolve("mass_effect.species");
    }

    public String cluster() {
        return faker.resolve("mass_effect.cluster");
    }

    public String planet() {
        return faker.resolve("mass_effect.planets");
    }

    public String quote(){
        return faker.resolve("mass_effect.quotes");
    }

}
