package net.datafaker;

/**
 * Breaking Bad is an American neo-Western crime drama television series.
 * <p>
 * Over the course of five seasons, Breaking Bad told a complex, character-driven story
 * of a man whose cancer diagnosis leads him to start cooking and selling meth to provide for his family.
 *
 * @since 1.0.0
 */
public class BreakingBad extends AbstractProvider {

    protected BreakingBad(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("breaking_bad.characters", this);
    }

    public String episode() {
        return faker.fakeValuesService().resolve("breaking_bad.episodes", this);
    }
}
