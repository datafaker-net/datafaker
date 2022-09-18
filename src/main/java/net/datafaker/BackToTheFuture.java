package net.datafaker;

/**
 * @since 0.8.0
 */
public class BackToTheFuture extends AbstractProvider<IProviders> {

    protected BackToTheFuture(BaseFaker faker) {
        super(faker);
    }

    public String character() {
        return resolve("back_to_the_future.characters");
    }

    public String date() {
        return resolve("back_to_the_future.dates");
    }

    public String quote() {
        return resolve("back_to_the_future.quotes");
    }
}
