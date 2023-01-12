package net.datafaker.providers.show;

import net.datafaker.providers.base.AbstractProvider;

/**
 * SpongeBob SquarePants (or simply SpongeBob) is an American animated comedy television series created by marine science educator and animator Stephen Hillenburg for Nickelodeon.
 *
 * @since 1.8.0
 */
public class Spongebob extends AbstractProvider<ShowProviders> {

    protected Spongebob(ShowProviders faker) {
        super(faker);
    }

    public String characters() {
        return resolve("spongebob.characters");
    }

    public String quotes() {
        return resolve("spongebob.quotes");
    }

    public String episodes() {
        return resolve("spongebob.episodes");
    }

}
