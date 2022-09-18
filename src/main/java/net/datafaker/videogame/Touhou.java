package net.datafaker.videogame;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.9.0
 */
public class Touhou extends AbstractProvider<VideoGameProviders> {

    protected Touhou(VideoGameProviders faker) {
        super(faker);
    }

    public String characterName() {
        return resolve("touhou.full_name");
    }

    public String characterFirstName() {
        return resolve("touhou.first_name");
    }

    public String characterLastName() {
        return resolve("touhou.last_name");
    }

    public String trackName() {
        return resolve("touhou.track_name");
    }

    public String gameName() {
        return resolve("touhou.game_name");
    }
}
