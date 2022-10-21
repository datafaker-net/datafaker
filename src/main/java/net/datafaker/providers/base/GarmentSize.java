package net.datafaker.providers.base;

/**
 * This class is used to generate garments sizes randomly.
 *
 * @since 1.6.0
 */

public class GarmentSize extends AbstractProvider<BaseProviders> {

    protected GarmentSize(BaseProviders faker) {
        super(faker);
    }

    /**
     * This method returns a garment size
     *
     * @return a string of garment size
     */
    public String size() {
        return resolve("garments_sizes.sizes");
    }
}
