package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Naruto is a Japanese manga series written and illustrated by Masashi Kishimoto, that tells the story of Naruto Uzumaki.
 *
 * @since 1.8.0
 */
public class Naruto extends AbstractProvider<ShowProviders> {

    protected Naruto(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("naruto.characters");
    }

    public String village() {
        return resolve("naruto.villages");
    }

    public String eye() {
        return resolve("naruto.eyes");
    }

    public String demon() {
        return resolve("naruto.demons");
    }

}
