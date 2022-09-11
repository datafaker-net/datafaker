package net.datafaker;

/**
 * @since 0.8.0
 */
public class HitchhikersGuideToTheGalaxy extends MovieProvider {

    protected HitchhikersGuideToTheGalaxy(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("hitchhikers_guide_to_the_galaxy.characters", this);
    }

    public String location() {
        return faker.fakeValuesService().resolve("hitchhikers_guide_to_the_galaxy.locations", this);
    }

    public String marvinQuote() {
        return faker.fakeValuesService().resolve("hitchhikers_guide_to_the_galaxy.marvin_quote", this);
    }

    public String planet() {
        return faker.fakeValuesService().resolve("hitchhikers_guide_to_the_galaxy.planets", this);
    }

    public String quote() {
        return faker.fakeValuesService().resolve("hitchhikers_guide_to_the_galaxy.quotes", this);
    }

    public String species() {
        return faker.fakeValuesService().resolve("hitchhikers_guide_to_the_galaxy.species", this);
    }

    public String starship() {
        return faker.fakeValuesService().resolve("hitchhikers_guide_to_the_galaxy.starships", this);
    }
}
