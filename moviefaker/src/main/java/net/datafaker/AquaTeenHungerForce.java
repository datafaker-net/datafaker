package net.datafaker;

/**
 * @since 0.8.0
 */
public class AquaTeenHungerForce extends MovieProvider {

    protected AquaTeenHungerForce(MovieFaker faker) {
        super(faker);
    }

    public String character() {
        return faker.fakeValuesService().resolve("aqua_teen_hunger_force.character", this);
    }

}
