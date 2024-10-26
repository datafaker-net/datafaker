package net.datafaker.providers.base;

/**
 * The Culture series is a science fiction series written by Scottish author Iain M. Banks and released from 1987 through to 2012.
 *
 * @since 1.7.0
 */
public class CultureSeries extends AbstractProvider<BaseProviders> {

    protected CultureSeries(BaseProviders faker) {
        super(faker);
    }

    public String books() {
        return resolve("culture_series.books");
    }

    public String cultureShips() {
        return resolve("culture_series.culture_ships");
    }

    public String cultureShipClasses() {
        return resolve("culture_series.culture_ship_classes");
    }

    public String cultureShipClassAbvs() {
        return resolve("culture_series.culture_ship_class_abvs");
    }

    public String civs() {
        return resolve("culture_series.civs");
    }

    public String planets() {
        return resolve("culture_series.planets");
    }

}
