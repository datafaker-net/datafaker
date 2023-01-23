package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Kingkiller Chronicle is a fantasy trilogy by the American writer Patrick Rothfuss.
 *
 * @since 1.8.0
 */
public class TheKingkillerChronicle extends AbstractProvider<EntertainmentProviders> {

    protected TheKingkillerChronicle(EntertainmentProviders faker) {
        super(faker);
    }

    public String book() {
        return resolve("the_kingkiller_chronicle.books");
    }

    public String character() {
        return resolve("the_kingkiller_chronicle.characters");
    }

    public String creature() {
        return resolve("the_kingkiller_chronicle.creatures");
    }

    public String location() {
        return resolve("the_kingkiller_chronicle.locations");
    }

}
