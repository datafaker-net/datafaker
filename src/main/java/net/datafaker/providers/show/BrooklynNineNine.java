package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Brooklyn Nine-Nine is an American police procedural comedy television series.
 *
 * @since 1.3.0
 */
public class BrooklynNineNine extends AbstractProvider<ShowProviders> {

    protected BrooklynNineNine(ShowProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("brooklyn_nine_nine.characters");
    }

    public String quotes() {
        return resolve("brooklyn_nine_nine.quotes");
    }

}
