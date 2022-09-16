package net.datafaker;

/**
 * @since 0.8.0
 */
public class AquaTeenHungerForce extends AbstractProvider {

    protected AquaTeenHungerForce(Faker faker) {
        super(faker);
    }

    public String character() {
        return resolve("aqua_teen_hunger_force.character");
    }

}
