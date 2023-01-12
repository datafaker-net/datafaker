package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Sword Art Online is a Japanese light novel series written by Reki Kawahara and illustrated by abec.
 *
 * @since 1.8.0
 */
public class SwordArtOnline extends AbstractProvider<ShowProviders> {

    protected SwordArtOnline(ShowProviders faker) {
        super(faker);
    }

    public String realName() {
        return resolve("sword_art_online.real_name");
    }

    public String gameName() {
        return resolve("sword_art_online.game_name");
    }

    public String location() {
        return resolve("sword_art_online.location");
    }

    public String item() {
        return resolve("sword_art_online.item");
    }

}
