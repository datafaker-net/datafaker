package net.datafaker.movie;

import net.datafaker.base.AbstractProvider;

/**
 * Breaking Bad is an American neo-Western crime drama television series.
 * <p>
 * Over the course of five seasons, Breaking Bad told a complex, character-driven story
 * of a man whose cancer diagnosis leads him to start cooking and selling meth to provide for his family.
 *
 * @since 1.0.0
 */
public class BreakingBad extends AbstractProvider<MovieProviders> {

    protected BreakingBad(MovieProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("breaking_bad.characters");
    }

    public String episode() {
        return resolve("breaking_bad.episodes");
    }
}
