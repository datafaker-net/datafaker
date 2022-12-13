package net.datafaker.providers.movie;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Avatar extends AbstractProvider<MovieProviders> {

    private static final Set DEFAULT_SET = Set.ROBOTS;
    private final String baseUrl;

    protected Avatar(MovieProviders faker) {
        super(faker);
        this.baseUrl = "https://robohash.org/";
    }

    public String image() {
        return image(DEFAULT_SET);
    }

    public String image(Set set) {
        return baseUrl + faker.regexify("[a-z]{8}") + "?set=set" + set.key + ".png";
    }

    public enum Set {
        ROBOTS(1),
        MONSTERS(2),
        ROBOT_HEADS(3),
        KITTENS(4);

        private final int key;

        Set(int key) {
            this.key = key;
        }
    }
}
