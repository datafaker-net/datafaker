package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * @since 1.8.0
 */
public class TheThickOfIt extends AbstractProvider<BaseProviders> {

    protected TheThickOfIt(BaseProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("the_thick_of_it.characters");
    }

    public String positions() {
        return resolve("the_thick_of_it.positions");
    }

    public String departments() {
        return resolve("the_thick_of_it.departments");
    }

}
