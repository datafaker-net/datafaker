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
        return resolve("mbti." + choice.get() + ".type");
    }

    public String name() {
        return resolve("mbti." + choice.get() + ".name");
    }

    public String characteristic() {
        return resolve("mbti." + choice.get() + ".characteristic");
    }

    public String personage() {
        return resolve("mbti." + choice.get() + ".personage");
    }

    public String merit() {
        return resolve("mbti." + choice.get() + ".merit");
    }

    public String weakness() {
        return resolve("mbti." + choice.get() + ".weakness");
    }


}
