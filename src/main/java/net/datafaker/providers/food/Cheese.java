package net.datafaker.providers.food;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Generates cheese-related fake data: type, texture, milk, color, name.
 * <p>
 * Data source:
 * <a href="https://www.cheese.com">cheese.com</a>
 * <a href="https://en.wikipedia.org/wiki/Types_of_cheese">Types_of_cheese</a>
 * </p>
 * @since 2.6.0
 */
public class Cheese extends AbstractProvider<FoodProviders> {

    protected Cheese(FoodProviders faker) {
        super(faker);
    }

    public String type() {
        return resolve("cheese.type");
    }

    public String texture() {
        return resolve("cheese.texture");
    }

    public String milk() {
        return resolve("cheese.milk");
    }

    public String color() {
        return resolve("cheese.color");
    }

    public String name() {
        return resolve("cheese.name");
    }

    /**
     * @return a complete wedge of cheese case bundling {@link #name()},
     * {@link #type()}, {@link #texture()}, {@link #color()}, and {@link #milk()}.
     */
    public Wedge wedge() {
        return new Wedge(name(), type(), texture(), color(), milk());
    }

    public record Wedge(String name, String type, String texture, String color, String milk) { }
}
