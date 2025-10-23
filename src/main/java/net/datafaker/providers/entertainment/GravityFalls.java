package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Gravity Falls is an American animated television series created by Alex Hirsch,
 * and produced by Disney Television Animation for Disney Channel and Disney XD.
 *
 * @author vicky-iv
 * @since 2.5.3
 */
public class GravityFalls extends AbstractProvider<EntertainmentProviders> {

    protected GravityFalls(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("gravity_falls.characters");
    }

    public String location() {
        return resolve("gravity_falls.locations");
    }

    public String creature() {
        return resolve("gravity_falls.creatures");
    }

    public String artifact() {
        return resolve("gravity_falls.artifacts");
    }

    public String quote() {
        return resolve("gravity_falls.quotes");
    }

    public String mabelSweater() {
        return resolve("gravity_falls.mabel_sweaters");
    }

    public String mysteryShackItem() {
        return resolve("gravity_falls.mystery_shack_items");
    }
}
