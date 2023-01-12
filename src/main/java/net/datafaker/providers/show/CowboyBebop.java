package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Cowboy Bebop is a Japanese neo-noir science fiction anime television series, which originally ran from 1998 to 1999.
 * @since 1.8.0
 */
public class CowboyBebop extends AbstractProvider<ShowProviders> {

    protected CowboyBebop(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("cowboy_bebop.character");
    }

    public String episode() {
        return resolve("cowboy_bebop.episode");
    }

    public String song() {
        return resolve("cowboy_bebop.song");
    }

    public String quote() {
        return resolve("cowboy_bebop.quote");
    }

}
