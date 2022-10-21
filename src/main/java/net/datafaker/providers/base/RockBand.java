package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class RockBand extends AbstractProvider<BaseProviders> {

    protected RockBand(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("rock_band.name");
    }
}
