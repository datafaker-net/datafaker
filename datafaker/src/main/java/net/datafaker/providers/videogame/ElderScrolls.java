package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Elder Scrolls is a series of action role-playing video games primarily developed by Bethesda Game Studios and published by Bethesda Softworks.
 *
 * @since 0.8.0
 */
public class ElderScrolls extends AbstractProvider<VideoGameProviders> {

    protected ElderScrolls(VideoGameProviders faker) {
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
