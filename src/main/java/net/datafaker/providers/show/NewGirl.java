package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * New Girl is an American television sitcom created by Elizabeth Meriwether.
 *
 * @since 1.8.0
 */
public class NewGirl extends AbstractProvider<BaseProviders> {

    protected NewGirl(BaseProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("new_girl.characters");
    }

    public String quotes() {
        return resolve("new_girl.quotes");
    }

}
