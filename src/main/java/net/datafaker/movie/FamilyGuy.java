package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

public class FamilyGuy extends AbstractProvider<MovieProviders> {

    protected FamilyGuy(MovieProviders faker) {
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
