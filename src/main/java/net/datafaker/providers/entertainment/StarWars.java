package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 1.6.0
 */
public class StarWars extends AbstractProvider<EntertainmentProviders> {

    private static final String[] CHARACTERS = {"admiral_ackbar", "ahsoka_tano", "anakin_skywalker", "asajj_ventress",
        "bendu", "boba_fett", "c_3po", "count_dooku", "darth_caedus", "darth_vader", "din_djarin", "emperor_palpatine", "finn",
        "general_hux", "grand_admiral_thrawn", "grand_moff_tarkin", "greedo", "grogu", "han_solo", "jabba_the_hutt",
        "jar_jar_binks", "k_2so", "kuiil", "kylo_ren", "lando_calrissian", "leia_organa", "luke_skywalker", "mace_windu",
        "maz_kanata", "moff_gideon", "obi_wan_kenobi", "padme_amidala", "qui_gon_jinn", "rey", "shmi_skywalker", "yoda"};

    protected StarWars(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("star_wars.characters");
    }

    public String callSign() {
        return faker.numerify(resolve("star_wars.call_sign"));
    }

    public String quotes() {
        return resolve("star_wars.quotes." + getFaker().options().option(CHARACTERS));
    }

    public String vehicles() {
        return resolve("star_wars.vehicles");
    }

    public String droids() {
        return resolve("star_wars.droids");
    }

    public String planets() {
        return resolve("star_wars.planets");
    }

    public String species() {
        return resolve("star_wars.species");
    }

    public String wookieWords() {
        return resolve("star_wars.wookiee_words");
    }

    public String alternateCharacterSpelling() {
        return resolve("star_wars.alternate_character_spellings." + getFaker().options().option(CHARACTERS));
    }
}
