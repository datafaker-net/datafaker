package net.datafaker;

/**
 * Myers-Briggs Type Indicator
 *
 * @since 1.5.0
 */
public class Mbti extends AbstractProvider {
    
    private final String choice;

    public Mbti(final Faker faker) {
        super(faker);
        this.choice = this.faker.resolve("mbti.choice");
    }

    public String type() {
        return faker.resolve("mbti.".concat(choice).concat(".type"));
    }

    public String name() {
        return faker.resolve("mbti.".concat(choice).concat(".name"));
    }

    public String characteristic() {
        return faker.resolve("mbti.".concat(choice).concat(".characteristic"));
    }

    public String personage() {
        return faker.resolve("mbti.".concat(choice).concat(".personage"));
    }

    public String merit(){return faker.resolve("mbti.".concat(choice).concat(".merit"));}

    public String weakness(){return faker.resolve("mbti.".concat(choice).concat(".weakness"));}


}
