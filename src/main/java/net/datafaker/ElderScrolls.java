package net.datafaker;

/**
 * @since 0.8.0
 */
public class ElderScrolls extends AbstractProvider {

    protected ElderScrolls(Faker faker) {
        super(faker);
    }

    public String race() {
        return resolve("games.elder_scrolls.race");
    }

    public String creature() {
        return resolve("games.elder_scrolls.creature");
    }

    public String region() {
        return resolve("games.elder_scrolls.region");
    }

    public String dragon() {
        return resolve("games.elder_scrolls.dragon");
    }

    public String city() {
        return resolve("games.elder_scrolls.city");
    }

    public String firstName() {
        return resolve("games.elder_scrolls.first_name");
    }

    public String lastName() {
        return resolve("games.elder_scrolls.last_name");
    }

    public String quote() {
        return resolve("games.elder_scrolls.quote");
    }
}
