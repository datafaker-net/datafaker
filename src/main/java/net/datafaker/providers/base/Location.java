package net.datafaker.providers.base;

/**
 * @since 2.3.0
 */
public class Location extends AbstractProvider<BaseProviders> {

    protected Location(BaseProviders faker) {
        super(faker);
    }

    public String building() {
        return resolve("location.building");
    }

    public String work() {
        return resolve("location.work");
    }

    public String nature() {
        return resolve("location.nature");
    }

    public String publicSpace() {
        return resolve("location.public_space");
    }

    public String privateSpace() {
        return resolve("location.private_space");
    }

    public String otherworldly() {
        return resolve("location.otherworldly");
    }

}
