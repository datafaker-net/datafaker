package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * @since 1.8.0
 */
public class Show extends AbstractProvider<BaseProviders> {

    protected Show(BaseProviders faker) {
        super(faker);
    }

    public String adultMusical() {
        return resolve("show.adult_musical");
    }

    public String play() {
        return resolve("show.play");
    }

    public String kidsMusical() {
        return resolve("show.kids_musical");
    }

}
