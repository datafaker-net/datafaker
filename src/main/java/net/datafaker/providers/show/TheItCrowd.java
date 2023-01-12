package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class TheItCrowd extends AbstractProvider<ShowProviders> {

    protected TheItCrowd(ShowProviders faker) {
        super(faker);
    }

    public String actors() {
        return resolve("the_it_crowd.actors");
    }

    public String characters() {
        return resolve("the_it_crowd.characters");
    }

    public String emails() {
        return resolve("the_it_crowd.emails");
    }

    public String quotes() {
        return resolve("the_it_crowd.quotes");
    }

}
