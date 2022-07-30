package net.datafaker;

/**
 * @since 0.8.0
 */
public class Book extends AbstractProvider {

    protected Book(Faker faker) {
        super(faker);
    }

    public String author() {
        return faker.fakeValuesService().resolve("book.author", this);
    }

    public String title() {
        return faker.fakeValuesService().resolve("book.title", this);
    }

    public String publisher() {
        return faker.fakeValuesService().resolve("book.publisher", this);
    }

    public String genre() {
        return faker.fakeValuesService().resolve("book.genre", this);
    }
}
