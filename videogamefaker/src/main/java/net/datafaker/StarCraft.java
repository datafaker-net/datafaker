package net.datafaker;

/**
 * @since 0.8.0
 */
public class StarCraft extends AbstractProvider<VideoGameFaker> {

    protected StarCraft(final VideoGameFaker faker) {
        super(faker);
    }

    public String unit() {
        return faker.fakeValuesService().resolve("starcraft.units", this);
    }

    public String building() {
        return faker.fakeValuesService().resolve("starcraft.buildings", this);
    }

    public String character() {
        return faker.fakeValuesService().resolve("starcraft.characters", this);
    }

    public String planet() {
        return faker.fakeValuesService().resolve("starcraft.planets", this);
    }

}
