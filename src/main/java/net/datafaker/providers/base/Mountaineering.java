package net.datafaker.providers.base;

/**
 * Mountaineering, or alpinism, is the set of outdoor activities that involves ascending tall mountains.
 *
 * @since 1.4.0
 */
public class Mountaineering extends AbstractProvider<BaseProviders> {

    protected Mountaineering(BaseProviders faker) {
        super(faker);
    }

    public String mountaineer() {
        return resolve("mountaineering.mountaineer");
    }

}
