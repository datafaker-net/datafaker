package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.7.0
 */
public class FamilyGuy extends AbstractProvider<ShowProviders> {

    protected FamilyGuy(ShowProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("family_guy.character");
    }

    public String location() {
        return resolve("family_guy.location");
    }

    public String quote() {
        return resolve("family_guy.quote");
    }

}
