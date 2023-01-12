package net.datafaker.providers.base;

/**
 * @since 1.8.0
 */
public class OlympicSport extends AbstractProvider<BaseProviders> {

    protected OlympicSport(BaseProviders faker) {
        super(faker);
    }

    public String summerOlympics() {
        return resolve("olympic_sport.summer_olympics");
    }

    public String winterOlympics() {
        return resolve("olympic_sport.winter_olympics");
    }

    public String summerParalympics() {
        return resolve("olympic_sport.summer_paralympics");
    }

    public String winterParalympics() {
        return resolve("olympic_sport.winter_paralympics");
    }

    public String ancientOlympics() {
        return resolve("olympic_sport.ancient_olympics");
    }

    public String unusual() {
        return resolve("olympic_sport.unusual");
    }

}
