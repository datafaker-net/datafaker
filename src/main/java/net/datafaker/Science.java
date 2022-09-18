package net.datafaker;

/**
 * @since 0.8.0
 */
public class Science extends AbstractProvider<IProviders> {

    protected Science(BaseFaker faker) {
        super(faker);
    }

    public String element() {
        return resolve("science.element");
    }

    public String elementSymbol() {
        return resolve("science.element_symbol");
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
