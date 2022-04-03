package net.datafaker;

/**
 * The Grateful Dead was an American rock band formed in 1965 in Palo Alto, California.
 * The band is known for its eclectic style, which fused elements of rock, folk, country, jazz, bluegrass, blues and rock.
 *
 * @since 1.4.0
 */
public class GratefulDead {

    private final Faker faker;

    protected GratefulDead(Faker faker) {
        this.faker = faker;
    }

    public String players() {
        return faker.fakeValuesService().resolve("grateful_dead.players", this, faker);
    }

    public String songs() {
        return faker.fakeValuesService().resolve("grateful_dead.songs", this, faker);
    }

}
