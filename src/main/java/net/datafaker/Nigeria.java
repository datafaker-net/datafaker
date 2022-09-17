package net.datafaker;

/**
 * Nigeria, officially the Federal Republic of Nigeria, is a country in West Africa.
 *
 * @since 1.2.0
 */
public class Nigeria extends AbstractProvider {
    private static final String KEY = "nigeria";

    protected Nigeria(Faker faker) {
        super(faker);
    }

    public String places() {
        return resolve(KEY + ".places");
    }

    public String name() {
        return resolve(KEY + ".name");
    }

    public String food() {
        return resolve(KEY + ".food");
    }

    public String schools() {
        return resolve(KEY + ".schools");
    }

    public String celebrities() {
        return resolve(KEY + ".celebrities");
    }
}



