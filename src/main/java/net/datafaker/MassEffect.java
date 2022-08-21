package net.datafaker;

public class MassEffect extends AbstractProvider {
    protected MassEffect(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("mass-effect.characters");
    }

    public String specie() {
        return faker.resolve("mass-effect.species");
    }

    public String cluster() {
        return faker.resolve("mass-effect.cluster");
    }

    public String planet() {
        return faker.resolve("mass-effect.planets");
    }

    public String quote(){
        return faker.resolve("mass-effect.quotes");
    }

}
