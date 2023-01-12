package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * South Park is an American animated television series created by Trey Parker and Matt Stone.
 *
 * @since 1.8.0
 */
public class SouthPark extends AbstractProvider<BaseProviders> {

    protected SouthPark(BaseProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("south_park.characters");
    }

    public String quotes() {
        return resolve("south_park.quotes");
    }

}
