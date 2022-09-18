package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class RockBand extends AbstractProvider<IProviders> {

    protected RockBand(BaseFaker faker) {
        super(faker);
    }

    public String name() {
        return resolve("rock_band.name");
    }
}
