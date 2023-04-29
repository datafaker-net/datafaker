package net.datafaker.providers.base;

/**
 * Provides different kind of transport.
 *
 * @since 2.0.0
 */
public class Transport extends AbstractProvider<BaseProviders> {

    public Transport(BaseProviders faker) {
        super(faker);
    }

    /**
     * @return transport type in the descriptive manner just like "Car", "Aircraft", etc.
     */
    public String type() {
        return resolve("transport.type");
    }
}
