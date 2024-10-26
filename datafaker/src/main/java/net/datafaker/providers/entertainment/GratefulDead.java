package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * The Grateful Dead was an American rock band formed in 1965 in Palo Alto, California.
 * The band is known for its eclectic style, which fused elements of rock, folk, country, jazz, bluegrass, blues and rock.
 *
 * @since 1.4.0
 */
public class GratefulDead extends AbstractProvider<EntertainmentProviders> {

    protected GratefulDead(EntertainmentProviders faker) {
        super(faker);
    }

    public String players() {
        return resolve("grateful_dead.players");
    }

    public String songs() {
        return resolve("grateful_dead.songs");
    }

}
