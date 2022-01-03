package net.datafaker;

public class RuPaulDragRace {

    private final Faker faker;

    protected RuPaulDragRace(Faker faker) {
        this.faker = faker;
    }

    public String queen() {
        return faker.resolve("rupaul.queens");
    }

    public String quote() {
        return faker.resolve("rupaul.quotes");
    }
}
