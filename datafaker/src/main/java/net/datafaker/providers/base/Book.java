package net.datafaker.providers.base;

/**
 * @since 0.8.0
 */
public class Book extends AbstractProvider<BaseProviders> {

    protected Book(BaseProviders faker) {
        super(faker);
    }

    public String author() {
        return resolve("book.author");
    }

    public String title() {
        return resolve("book.title");
    }

    public String publisher() {
        return resolve("book.publisher");
    }

    public String genre() {
        return resolve("book.genre");
    }
}
