package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * New Girl is an American television sitcom created by Elizabeth Meriwether.
 *
 * @since 1.8.0
 */
public class NewGirl extends AbstractProvider<EntertainmentProviders> {

    protected NewGirl(EntertainmentProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("new_girl.characters");
    }

    public String quotes() {
        return resolve("new_girl.quotes");
    }

}
