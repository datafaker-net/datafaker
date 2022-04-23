package net.datafaker;

/**
 * @author Uncle Road
 * A class for generating random value of weapon
 */

public class Weapon {
    private final Faker faker;

    protected Weapon(final Faker faker){
        this.faker = faker;
    }

    /**
     * @return a random value of weapon name
     */
    public String getWeapon(){
        String type = faker.resolve("weapon.type");
        String types = faker.resolve("weapon.".concat(type));
        String res = faker.resolve("weapon.".concat(types));
        return res;
    }

    /**
     * @return a random value of provenance
     */
    public String provenance(){
        String pro = faker.resolve("weapon.provenance");
        return pro;
    }

    /**
     * @return a random value of user
     */
    public String user() {
        String user = faker.resolve("weapon.user");
        return user;
    }

    /**
     * @return a random value of function
     */
    public String function(){
        String func = faker.resolve("weapon.function");
        return func;
    }

    /**
     * @return a random value of target
     */
    public String target() {
        String tar = faker.resolve("weapon.target");
        return tar;
    }

    /**
     * @return a random value of type
     */
    public String type() {
        String type = faker.resolve("weapon.type");
        return type;
    }
}
