package net.datafaker.providers.videogame;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Touhou Project, also known simply as Touhou, is a bullet hell shoot 'em up video game series created by one-man independent Japanese doujin soft developer Team Shanghai Alice.
 *
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
