package net.datafaker.providers.entertainment;

import net.datafaker.providers.base.AbstractProvider;

/**
 * @since 0.8.0
 */
public class Dune extends AbstractProvider<EntertainmentProviders> {

    protected Dune(EntertainmentProviders faker) {
        super(faker);
    }

    public String character() {
        return resolve("dune.characters");
    }

    public String title() {
        return resolve("dune.titles");
    }

    public String planet() {
        return resolve("dune.planets");
    }

    public String quote() {
        return quote(faker.options().option(Dune.Quote.class));
    }

    public String quote(Quote quote) {
        return resolve("dune.quotes." + quote.yamlKey);
    }

    public String saying() {
        return saying(faker.options().option(Dune.Saying.class));
    }

    public String saying(Saying saying) {
        return resolve("dune.sayings." + saying.yamlKey);
    }

    public enum Quote {
        GUILD_NAVIGATOR("guild_navigator"),
        EMPEROR("emperor"),
        PAUL("paul"),
        THUFIR("thufir"),
        JESSICA("jessica"),
        IRULAN("irulan"),
        MOHIAM("mohiam"),
        GURNEY("gurney"),
        LETO("leto"),
        STILGAR("stilgar"),
        LIET_KYNES("liet_kynes"),
        PARDOT_KYNES("pardot_kynes"),
        BARON_HARKONNEN("baron_harkonnen"),
        PITER("piter"),
        ALIA("alia"),
        MAPES("mapes"),
        DUNCAN("duncan"),
        YUEH("yueh");

        private final String yamlKey;

        Quote(String yamlKey) {
            this.yamlKey = yamlKey;
        }
    }

    public enum Saying {
        BENE_GESSERIT("bene_gesserit"),
        FREMEN("fremen"),
        MENTAT("mentat"),
        MUADDIB("muaddib"),
        ORANGE_CATHOLIC_BIBLE("orange_catholic_bible");

        private final String yamlKey;

        Saying(String yamlKey) {
            this.yamlKey = yamlKey;
        }
    }

}
