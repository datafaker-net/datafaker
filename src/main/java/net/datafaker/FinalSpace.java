package net.datafaker;

public class FinalSpace extends AbstractProvider {

    protected FinalSpace(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.resolve("final_space.characters");
    }

    public String vehicle() {
        return faker.resolve("final_space.vehicles");
    }

    public String quote() {
        return faker.resolve("final_space.quotes");
    }

}
