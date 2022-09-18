package net.datafaker;

/**
 * @since 1.4.0
 */
public class ElectricalComponents extends AbstractProvider<IProviders> {

    protected ElectricalComponents(BaseFaker faker) {
        super(faker);
    }

    public String active() {
        return resolve("electrical_components.active");
    }

    public String passive() {
        return resolve("electrical_components.passive");
    }

    public String electromechanical() {
        return resolve("electrical_components.electromechanical");
    }
}
