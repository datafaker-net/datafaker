package net.datafaker;

/**
 * RuPaul's Drag Race is a reality competition series produced by World of Wonder for the Logo TV Network.
 *
 * @since 1.0.0
 */
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
