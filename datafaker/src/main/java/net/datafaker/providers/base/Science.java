package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Science extends AbstractProvider<BaseProviders> {

    protected Science(BaseProviders faker) {
        super(faker);
    }

    public String element() {
        return resolve("science.element");
    }

    public String elementSymbol() {
        return resolve("science.element_symbol");
    }

    public String unit() {
        return resolve("science.unit");
    }

    public String scientist() {
        return resolve("science.scientist");
    }

    public String tool() {
        return resolve("science.tool");
    }

    public String quark() {
        return resolve("science.particles.quarks");
    }

    public String leptons() {
        return resolve("science.particles.leptons");
    }

    public String bosons() {
        return resolve("science.particles.bosons");
    }

}
