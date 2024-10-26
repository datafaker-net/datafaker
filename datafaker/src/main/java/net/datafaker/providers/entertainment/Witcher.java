package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Witcher extends AbstractProvider<EntertainmentProviders> {

    protected Witcher(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("games.witcher.characters");
    }

    public String witcher() {
        return resolve("games.witcher.witchers");
    }

    public String school() {
        return resolve("games.witcher.schools");
    }

    public String location() {
        return resolve("games.witcher.locations");
    }

    public String quote() {
        return resolve("games.witcher.quotes");
    }

    public String monster() {
        return resolve("games.witcher.monsters");
    }

    public String sign() {
        return resolve("games.witcher.signs");
    }

    public String potion() {
        return resolve("games.witcher.potions");
    }

    public String book() {
        return resolve("games.witcher.books");
    }
}
