package net.datafaker.providers.base;

/**
 * Generates emergency-related fake data: the nature of an emergency
 * (fire, flood, tornado, ...), a plausible emergency location, and an
 * instruction someone might be asked to follow in response.
 *
 * @since 2.7.0
 */
public class Emergency extends AbstractProvider<BaseProviders> {

    protected Emergency(BaseProviders faker) {
        super(faker);
    }

    /**
     * @return a type of emergency, e.g. "Fire", "Flood", "Tornado".
     */
    public String nature() {
        return resolve("emergency.nature");
    }

    /**
     * @return a plausible location where an emergency might occur,
     * e.g. "Highway", "Downtown office building".
     */
    public String location() {
        return resolve("emergency.location");
    }

    /**
     * @return an instruction to follow during an emergency, e.g.
     * "Evacuate the building", "Shelter in place".
     */
    public String instruction() {
        return resolve("emergency.instruction");
    }
}
