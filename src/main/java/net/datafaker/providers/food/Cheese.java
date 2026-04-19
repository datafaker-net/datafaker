package net.datafaker.providers.food;

import net.datafaker.providers.base.AbstractProvider;

/**
 * Generates cheese-related fake data: type, texture, milk, color, name etc.
 * <p>
 * Data source:
 * <a href="https://www.cheese.com">cheese.com</a>
 * <a href="https://en.wikipedia.org/wiki/Types_of_cheese">Types of cheese</a>
 * <a href="https://www.tasteatlas.com/cheese/products">Cheese producers</a>
 * <a href="https://www.wisconsincheese.com/the-cheese-life/article/31/cheese-rinds">Cheese rinds</a>
 * <a href="https://www.bluecart.com/blog/cheese-packaging-materials">Cheese packaging</a>
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

    public String producer() {
        return resolve("cheese.producer");
    }

    public String rind() {
        return resolve("cheese.rind");
    }

    public String rindEdibility () {
        return resolve("cheese.rind_edibility");
    }

    public String packaging () {
        return resolve("cheese.packaging");
    }

    /**
     * @return a complete wedge of cheese case bundling {@link #name()},
     * {@link #type()}, {@link #producer()}, {@link #texture()}, {@link #color()},
     * {@link #milk(), {@link #rind(), {@link #rindEdibility(), and {@link #packaging ()}.
     */
    public Wedge wedge() {
        return new Wedge(name(), type(), producer(), texture(), color(), milk(), rind(), rindEdibility(), packaging());
    }

    public record Wedge(String name, String type, String producer, String texture, String color, String milk,
                        String rind, String rindEdibility, String packaging) { }
}
