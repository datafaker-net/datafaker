package net.datafaker;

/**
 * @since 1.4.0
 */
public class EldenRing extends AbstractProvider {

    protected EldenRing(Faker faker) {
        super(faker);
    }

    public String location() {
        return faker.resolve("elden_ring.location");
    }

    public String weapon() {
        return faker.resolve("elden_ring.weapon");
    }

    public String skill() {
        return faker.resolve("elden_ring.skill");
    }

    public String spell() {
        return faker.resolve("elden_ring.spell");
    }

    public String npc() {
        return faker.resolve("elden_ring.npc");
    }
}
