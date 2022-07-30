package net.datafaker;

/**
 * Tron is a 1982 American science fiction action-adventure film.
 *
 * @since 1.4.0
 */
public class Tron extends AbstractProvider {

    protected Tron(Faker faker) {
        super(faker);
    }

    public String character() {
        return character(faker.options().option(Character.class));
    }

    public String character(Character character) {
        return faker.fakeValuesService().resolve("tron.characters." + character.yamlKey, this);
    }

    public String game() {
        return faker.fakeValuesService().resolve("tron.games", this);
    }

    public String location() {
        return faker.fakeValuesService().resolve("tron.locations", this);
    }

    public String quote() {
        return quote(faker.options().option(Tron.Quote.class));
    }

    public String quote(Tron.Quote quote) {
        return faker.fakeValuesService().resolve("tron.quotes." + quote.yamlKey, this);
    }

    public String tagline() {
        return faker.fakeValuesService().resolve("tron.taglines", this);
    }

    public String vehicle() {
        return faker.fakeValuesService().resolve("tron.vehicles", this);
    }

    public String alternateCharacterSpelling() {
        return alternateCharacterSpelling(faker.options().option(Tron.AlternateCharacterSpelling.class));
    }

    public String alternateCharacterSpelling(AlternateCharacterSpelling alternateCharacterSpelling) {
        return faker.fakeValuesService().resolve("tron.alternate_character_spellings." + alternateCharacterSpelling.yamlKey, this);
    }

    public enum AlternateCharacterSpelling {
        ALAN_BRADLEY("alan_bradley"),
        CLU("clu"),
        DR_LORA_BAINES("dr_lora_baines"),
        DR_WALTER_GIBBS("dr_walter_gibbs"),
        ED_DILLINGER("ed_dillinger"),
        KEVIN_FLYNN("kevin_flynn"),
        MCP("mcp"),
        ROY_KLEINBERG("roy_kleinberg");

        private final String yamlKey;

        AlternateCharacterSpelling(String yamlKey) {
            this.yamlKey = yamlKey;
        }

    }

    public enum Quote {
        ALAN_BRADLEY("alan_bradley"),
        BIT("bit"),
        CLU("clu"),
        CROM("crom"),
        DR_LORA_BAINES("dr_lora_baines"),
        DR_WALTER_GIBBS("dr_walter_gibbs"),
        DUMONT("dumont"),
        ED_DILLINGER("ed_dillinger"),
        KEVIN_FLYNN("kevin_flynn"),
        MCP("mcp"),
        PROGRAM("program"),
        RAM("ram"),
        SARK("sark"),
        TRON("tron"),
        YORI("yori");

        private final String yamlKey;

        Quote(String yamlKey) {
            this.yamlKey = yamlKey;
        }
    }

    public enum Character {
        OTHER("other"),
        PROGRAM("programs"),
        USER("users");

        private final String yamlKey;

        Character(String yamlKey) {
            this.yamlKey = yamlKey;
        }
    }
}


