package net.datafaker;

public class StarWars extends AbstractProvider {

    private final String[] CHARACTERS = new String[]{"admiral_ackbar", "ahsoka_tano", "anakin_skywalker", "asajj_ventress",
            "bendu", "boba_fett", "c_3po", "count_dooku", "darth_caedus", "darth_vader", "emperor_palpatine", "finn",
            "general_hux", "grand_admiral_thrawn", "grand_moff_tarkin", "greedo", "han_solo", "jabba_the_hutt",
            "jar_jar_binks", "k_2so", "kylo_ren", "lando_calrissian", "leia_organa", "luke_skywalker", "mace_windu",
            "maz_kanata", "obi_wan_kenobi", "padme_amidala", "qui_gon_jinn", "rey", "shmi_skywalker", "yoda"};

    protected StarWars(Faker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("star_wars.characters", this, faker);
    }

    public String callSign() {
        return faker.fakeValuesService().resolve("star_wars.call_sign", this, faker);
    }

    public String quotes() {
        return faker.fakeValuesService().resolve("star_wars.quotes." + CHARACTERS[faker.random().nextInt(CHARACTERS.length)], this, faker);
    }
    public String vehicles() {
        return faker.fakeValuesService().resolve("star_wars.vehicles", this, faker);
    }

    public String droids() {
        return faker.fakeValuesService().resolve("star_wars.droids", this, faker);
    }

    public String planets() {
        return faker.fakeValuesService().resolve("star_wars.planets", this, faker);
    }

    public String species() {
        return faker.fakeValuesService().resolve("star_wars.species", this, faker);
    }

    public String wookieWords() {
        return faker.fakeValuesService().resolve("star_wars.wookiee_words", this, faker);
    }

    public String alternateCharacterSpelling() {
        return faker.fakeValuesService().resolve("star_wars.alternate_character_spellings." + CHARACTERS[faker.random().nextInt(CHARACTERS.length)], this, faker);
    }
}
