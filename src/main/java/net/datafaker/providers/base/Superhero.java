package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Superhero extends AbstractProvider<BaseProviders> {

    protected Superhero(BaseProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("superhero.name");
    }

    public String prefix() {
        return resolve("superhero.prefix");
    }

    public String suffix() {
        return resolve("superhero.suffix");
    }

    public String power() {
        return resolve("superhero.power");
    }

    public String descriptor() {
        return resolve("superhero.descriptor");
    }
}
