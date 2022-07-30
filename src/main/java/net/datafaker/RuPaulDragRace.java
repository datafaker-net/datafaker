package net.datafaker;

/**
 * RuPaul's Drag Race is a reality competition series produced by World of Wonder for the Logo TV Network.
 *
 * @since 1.0.0
 */
public class RuPaulDragRace extends AbstractProvider {

    protected RuPaulDragRace(Faker faker) {
        super(faker);
    }

    public String queen() {
        return faker.resolve("rupaul.queens");
    }

    public String quote() {
        return faker.resolve("rupaul.quotes");
    }
}
