package net.datafaker;

/**
 * @since 0.9.0
 */
public class Touhou extends AbstractProvider<VideoGameFaker> {

    protected Touhou(VideoGameFaker faker) {
        super(faker);
    }

    public String characterName() {
        return faker.fakeValuesService().resolve("touhou.full_name", this);
    }

    public String characterFirstName() {
        return faker.fakeValuesService().resolve("touhou.first_name", this);
    }

    public String characterLastName() {
        return faker.fakeValuesService().resolve("touhou.last_name", this);
    }

    public String trackName() {
        return faker.fakeValuesService().resolve("touhou.track_name", this);
    }

    public String gameName() {
        return faker.fakeValuesService().resolve("touhou.game_name", this);
    }
}
