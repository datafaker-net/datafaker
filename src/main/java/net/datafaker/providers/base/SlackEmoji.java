package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class SlackEmoji extends AbstractProvider<BaseProviders> {

    protected SlackEmoji(BaseProviders faker) {
        super(faker);
    }

    public String people() {
        return resolve("slack_emoji.people");
    }

    public String nature() {
        return resolve("slack_emoji.nature");
    }

    public String foodAndDrink() {
        return resolve("slack_emoji.food_and_drink");
    }

    public String celebration() {
        return resolve("slack_emoji.celebration");
    }

    public String activity() {
        return resolve("slack_emoji.activity");
    }

    public String travelAndPlaces() {
        return resolve("slack_emoji.travel_and_places");
    }

    public String objectsAndSymbols() {
        return resolve("slack_emoji.objects_and_symbols");
    }

    public String custom() {
        return resolve("slack_emoji.custom");
    }

    public String emoji() {
        return resolve("slack_emoji.emoji");
    }
}
