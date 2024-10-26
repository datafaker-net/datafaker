package net.datafaker.providers.base;

/**
 * Military ranks.
 *
 * @since 1.2.0
 */
public class Military extends AbstractProvider<BaseProviders> {

    protected Military(BaseProviders faker) {
        super(faker);
    }

    public String armyRank() {
        return resolve("military.army_rank");
    }

    public String marinesRank() {
        return resolve("military.marines_rank");
    }

    public String navyRank() {
        return resolve("military.navy_rank");
    }

    public String airForceRank() {
        return resolve("military.air_force_rank");
    }

    public String dodPaygrade() {
        return resolve("military.dod_paygrade");
    }

}
