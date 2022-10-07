package net.datafaker.providers.base;


/**
 * Dungeons and Dragons is a fantasy tabletop role-playing game originally designed by Gary Gygax and Dave Arneson.
 * <p>
 * This faker is dedicated to Bart van Kuik, a lifelong DnD player, and lifelong friend.
 *
 * @since 1.7.0
 */
public class DungeonsAndDragons extends AbstractProvider<BaseProviders> {

    protected DungeonsAndDragons(BaseProviders faker) {
        super(faker);
    }

    public String alignments() {
        return resolve("dnd.alignments");
    }

    public String backgrounds() {
        return resolve("dnd.backgrounds");
    }

    public String cities() {
        return resolve("dnd.cities");
    }

    public String klasses() {
        return resolve("dnd.klasses");
    }

    public String languages() {
        return resolve("dnd.languages");
    }

    public String meleeWeapons() {
        return resolve("dnd.melee_weapons");
    }

    public String monsters() {
        return resolve("dnd.monsters");
    }

    public String races() {
        return resolve("dnd.races");
    }

    public String rangedWeapons() {
        return resolve("dnd.ranged_weapons");
    }

}
