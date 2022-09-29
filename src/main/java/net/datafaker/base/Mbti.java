package net.datafaker.base;

/**
 * Myers-Briggs Type Indicator
 *
 * @since 1.5.0
 */
public class Mbti extends AbstractProvider<BaseProviders> {

    private final String choice;

    public Mbti(final BaseProviders faker) {
        super(faker);
        this.choice = this.faker.resolve("mbti.choice");
    }

    public String type() {
        return resolve("mbti.".concat(choice).concat(".type"));
    }

    public String name() {
        return resolve("mbti.".concat(choice).concat(".name"));
    }

    public String characteristic() {
        return resolve("mbti.".concat(choice).concat(".characteristic"));
    }

    public String personage() {
        return resolve("mbti.".concat(choice).concat(".personage"));
    }

    public String merit() {
        return resolve("mbti.".concat(choice).concat(".merit"));
    }

    public String weakness() {
        return resolve("mbti.".concat(choice).concat(".weakness"));
    }


}
