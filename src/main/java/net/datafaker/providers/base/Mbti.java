package net.datafaker.providers.base;

import java.util.function.Supplier;

/**
 * Myers-Briggs Type Indicator
 *
 * @since 1.5.0
 */
public class Mbti extends AbstractProvider<BaseProviders> {

    private final Supplier<String> choice;

    public Mbti(final BaseProviders faker) {
        super(faker);
        this.choice = () -> this.faker.resolve("mbti.choice");
    }

    public String type() {
        return resolve("mbti.".concat(choice.get()).concat(".type"));
    }

    public String name() {
        return resolve("mbti.".concat(choice.get()).concat(".name"));
    }

    public String characteristic() {
        return resolve("mbti.".concat(choice.get()).concat(".characteristic"));
    }

    public String personage() {
        return resolve("mbti.".concat(choice.get()).concat(".personage"));
    }

    public String merit() {
        return resolve("mbti.".concat(choice.get()).concat(".merit"));
    }

    public String weakness() {
        return resolve("mbti.".concat(choice.get()).concat(".weakness"));
    }


}
