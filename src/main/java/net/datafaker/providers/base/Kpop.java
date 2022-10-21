package net.datafaker.providers.base;

/**
 * K-pop, short for Korean popular music, is a genre of music originating in South Korea as part of South Korean culture.
 *
 * @since 1.3.0
 */
public class Kpop extends AbstractProvider<BaseProviders> {

    protected Kpop(BaseProviders faker) {
        super(faker);
    }

    public String iGroups() {
        return resolve("kpop.i_groups");
    }

    public String iiGroups() {
        return resolve("kpop.ii_groups");
    }

    public String iiiGroups() {
        return resolve("kpop.iii_groups");
    }

    public String girlGroups() {
        return resolve("kpop.girl_groups");
    }

    public String boyBands() {
        return resolve("kpop.boy_bands");
    }

    public String solo() {
        return resolve("kpop.solo");
    }

}
