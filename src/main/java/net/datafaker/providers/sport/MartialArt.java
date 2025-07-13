package net.datafaker.providers.sport;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Custom Faker provider for martial arts.
 * Supports resolving values from "martial_art.name" and "martial_art.origin" in the locale YAML file.
 *
 * <p>Contributed by Paulo Cunha</p>
 *
 * @see <a href="https://github.com/paulofranklins2/">https://github.com/paulofranklins2/</a>
 * @see <a href="https://www.linkedin.com/in/paulofranklins2/">https://www.linkedin.com/in/paulofranklins/</a>
 *
 * @since 2.4.4
 */
public class MartialArt extends AbstractProvider<SportProviders> {

    protected MartialArt(SportProviders faker) {
        super(faker);
    }

    public String name() {
        return resolve("martial_art.name");
    }
}
