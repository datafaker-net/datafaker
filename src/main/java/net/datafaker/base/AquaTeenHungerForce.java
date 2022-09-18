package net.datafaker.base;

/**
 * @since 0.8.0
 */
public class AquaTeenHungerForce extends AbstractProvider<IProviders> {

    protected AquaTeenHungerForce(BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("aqua_teen_hunger_force.character");
    }

}
