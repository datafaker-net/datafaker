package net.datafaker;

/**
 * @since 1.4.0
 */
public class EldenRing extends AbstractProvider {

    protected EldenRing(Faker faker) {
        super(faker);
    }

    public String location() {
        return resolve("elden_ring.location");
    }

    public String weapon() {
        return resolve("elden_ring.weapon");
    }

    public String skill(){
        return resolve("elden_ring.skill");
    }

    public String spell(){
        return resolve("elden_ring.spell");
    }

    public String npc(){
        return resolve("elden_ring.npc");
    }
}
