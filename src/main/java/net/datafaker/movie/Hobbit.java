package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Hobbit extends AbstractProvider<MovieProviders> {

    protected Hobbit(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("hobbit.character");
    }

    public String thorinsCompany() {
        return resolve("hobbit.thorins_company");
    }

    public String quote() {
        return resolve("hobbit.quote");
    }

    public String location() {
        return resolve("hobbit.location");
    }
}
