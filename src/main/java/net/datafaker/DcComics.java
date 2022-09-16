package net.datafaker;

/**
 * @since 1.5.0
 */
public class DcComics extends AbstractProvider {

    public DcComics(Faker faker) {
        super(faker);
    }

    public String hero() {
        return resolve("dc_comics.hero");
    }

    public String heroine() {
        return resolve("dc_comics.heroine");
    }

    public String villain() {
        return resolve("dc_comics.villain");
    }

    public String name() {
        return resolve("dc_comics.name");
    }

    public String title() {
        return resolve("dc_comics.title");
    }
}
