package net.datafaker.base;

/**
 * @since 1.4.0
 */
public class EldenRing extends AbstractProvider<IProviders> {

    protected EldenRing(BaseFaker faker) {
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
