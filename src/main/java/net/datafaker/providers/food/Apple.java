package net.datafaker.providers.food;

import net.datafaker.providers.base.AbstractProvider;
import net.datafaker.providers.base.BaseProviders;

/**
 * @since 2.5.2
 */
public class Apple extends AbstractProvider<BaseProviders> {

    protected Apple(BaseProviders faker) {
        super(faker);
    }

    public String type() {
        return resolve("apple.type");
    }

    public String color() {
        return resolve("apple.color");
    }

}
